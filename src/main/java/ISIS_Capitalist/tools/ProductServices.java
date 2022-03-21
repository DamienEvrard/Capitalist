package ISIS_Capitalist.tools;

import generated.PallierType;
import generated.ProductType;
import generated.World;
import java.io.FileNotFoundException;
import javax.xml.bind.JAXBException;

public class ProductServices {

    private WorldServices worldServices;
    private ApplyUpgrade applyUpgrade;

    public ProductServices() {
        this.worldServices = new WorldServices();
        applyUpgrade=new ApplyUpgrade();
    }

    /**
     * prend en paramètre le pseudo du joueur et le produit sur lequel une
     * action a eu lieu (lancement manuel de production ou achat d’une certaine
     * quantité de produit) renvoie false si l’action n’a pas pu être traitée
     *
     * @param username = pseudo du joueur
     * @param newproduct = produit client
     */
    public Boolean updateProduct(String username, ProductType newproduct) throws JAXBException, FileNotFoundException {
        World world = worldServices.getWorld(username);
        ProductType product = findProductById(world, newproduct.getId());
        if (product == null) {
            return false;
        }
        int qtchange = newproduct.getQuantite() - product.getQuantite();
        if (qtchange > 0) {
            double cout = 0;
            for (int i = 0; i < qtchange; i++) {
                cout = cout + coutDachatDesProduits(product, i);
            }
            if (world.getMoney() >= cout) {
                world.setMoney(world.getMoney() - cout);
                System.out.println(cout);
                product.setCout(coutDachatDesProduits(product, qtchange));
                product.setQuantite(newproduct.getQuantite());
                checkIfUnlockIsAvailable(product, world);
                worldServices.checkUpgradeIsAvailable(world);

            }
        }else {
            product.setTimeleft(product.getVitesse());
        }
        worldServices.saveWorldToXml(world, username);
        return true;
    }

    public ProductType findProductById(World world, int id) {
        for (ProductType pt : world.getProducts().getProduct()) {
            if (id == pt.getId()) {
                return pt;
            }
        }
        return null;
    }

    public double coutDachatDesProduits(ProductType product, int qte) {
        return product.getCout() * Math.pow(product.getCroissance(), qte);
    }

    private double calculRevenu(ProductType product, int qte, World world) {
        double result = 0;
        result = product.getRevenu() * qte;
        if (world.getActiveangels() >= 1) {
            result = result * (1 + world.getActiveangels() * world.getAngelbonus() / 100);
        }
        return result;
    }
    
    public boolean checkIfUnlockIsAvailable(ProductType product,World world){
        for(PallierType pt:product.getPalliers().getPallier()){
            if(product.getQuantite()>=pt.getSeuil() && pt.isUnlocked()==false){
                pt.setUnlocked(true);
                switch(pt.getTyperatio()){
                    case GAIN:
                        applyUpgrade.applyUpgradeGain(pt, product);
                        break;
                    case VITESSE:
                        applyUpgrade.applyUpgradeVitesse(pt, product);
                        break;
                }
            }
        }
        
        return true;
    }


}
