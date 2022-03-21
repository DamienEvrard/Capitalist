package ISIS_Capitalist.WebService;

import ISIS_Capitalist.tools.ManagerServices;
import generated.PallierType;
import java.io.FileNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBException;
import org.springframework.web.bind.annotation.RequestBody;

@Path("generic")
public class WebServiceManager {
    
    ManagerServices services;

    public WebServiceManager() {
        services = new ManagerServices();
    }
    
    @PUT
    @Path("manager")
    @Consumes({"application/xml","application/json"})
    public void updateManager(@Context HttpServletRequest request,@RequestBody PallierType newManager) throws JAXBException, FileNotFoundException{
        String user = request.getHeader("X-user");
        services.updateManager(user, newManager);
    }
        
}
