package ISIS_Capitalist.tools;

import generated.PallierType;
import generated.ProductType;
import generated.World;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.xml.bind.JAXBException;

public class ManagerServices {

    private WorldServices worldServices;
    private ProductServices productService;

    public ManagerServices() {
        this.worldServices = new WorldServices();
        this.productService = new ProductServices();
    }

    public Boolean updateManager(String username, PallierType newmanager) throws JAXBException, FileNotFoundException {
        World world = worldServices.getWorld(username);
        PallierType manager = findManagerByName(world, newmanager.getName());
        if (manager == null) {
            return false;
        }
        ProductType product = productService.findProductById(world, manager.getIdcible());
        if (product == null) {
            return false;
        }

        if (world.getMoney() >= manager.getSeuil()) {
            world.setMoney(world.getMoney() - manager.getSeuil());
            manager.setUnlocked(true);
            product.setManagerUnlocked(true);
        }else{
            return false;
        }
        worldServices.saveWorldToXml(world, username);
        return true;
    }

    private PallierType findManagerByName(World world, String name) {
        for (PallierType pt : world.getManagers().getPallier()) {
            if (name.equals(pt.getName())) {
                return pt;
            }
        }
        return null;
    }
}
