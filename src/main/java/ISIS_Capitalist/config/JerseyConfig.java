package ISIS_Capitalist.config;


import ISIS_Capitalist.WebService.WebServiceAngelUpgrade;
import ISIS_Capitalist.WebService.WebServiceManager;
import ISIS_Capitalist.WebService.WebServiceProduct;
import ISIS_Capitalist.WebService.WebServiceUpgrade;
import ISIS_Capitalist.WebService.WebserviceWorld;
import ISIS_Capitalist.security.CORSResponseFilter;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/adventureisis")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(WebserviceWorld.class);
        register(WebServiceProduct.class);
        register(WebServiceManager.class);
        register(WebServiceUpgrade.class);
        register(WebServiceAngelUpgrade.class);
        register(CORSResponseFilter.class);
    }

}
