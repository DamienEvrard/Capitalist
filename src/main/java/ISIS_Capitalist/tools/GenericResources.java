package ISIS_Capitalist.tools;

import com.google.gson.Gson;
import generated.PallierType;
import generated.ProductType;
import generated.World;

public class GenericResources {

    public GenericResources() {
    }

    public ProductType convertJSONToProductType(String json) {
        return new Gson().fromJson(json, ProductType.class);
    }

    public PallierType convertJSONToPallierType(String json) {
        return new Gson().fromJson(json, PallierType.class);
    }

    public void updateScore(World world) {
        long deltaTime = System.currentTimeMillis() - world.getLastupdate();
        world.setLastupdate(System.currentTimeMillis());
        if (deltaTime > 0) {
            for (ProductType product : world.getProducts().getProduct()) {

                if (product.isManagerUnlocked()) {
                    int quantiteProduit = (int) (deltaTime / product.getVitesse());
                    world.setScore(world.getScore() + calculRevenu(product, quantiteProduit, world));
                    world.setMoney(world.getMoney() + calculRevenu(product, quantiteProduit, world));

                } else {
                    if (product.getTimeleft() != 0 && product.getTimeleft() < deltaTime) {
                        world.setScore(world.getScore() + calculRevenu(product, 1, world));
                        world.setMoney(world.getMoney() + calculRevenu(product, 1, world));
                        product.setTimeleft(0);
                    } else if (product.getQuantite() != 0 && product.getTimeleft() != 0 && product.getTimeleft() > deltaTime) {
                        product.setTimeleft(product.getTimeleft() - deltaTime);
                    }
                }
            }
        }
    }

    private double calculRevenu(ProductType product, int qte, World world) {
        double result = 0;
        result = product.getRevenu() * qte * product.getQuantite();

        if (world.getActiveangels() >= 1) {
            result = result * (1 + world.getActiveangels() * world.getAngelbonus() / 100);
        }
        return result;
    }
}
