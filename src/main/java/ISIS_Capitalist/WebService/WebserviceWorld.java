package ISIS_Capitalist.WebService;

import ISIS_Capitalist.tools.WorldServices;
import generated.World;
import java.io.FileNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.xml.bind.JAXBException;

@Path("generic")
public class WebserviceWorld {

    WorldServices services;

    public WebserviceWorld() {
        services = new WorldServices();
    }

    @GET
    @Path("world")
    @Produces({"application/xml", "application/json"})
    public World getWorld(@Context HttpServletRequest request) throws JAXBException, FileNotFoundException{
        String user = request.getHeader("X-user");
        return services.getWorld(user);
    }

    @DELETE
    @Path("world")
    @Consumes({"application/xml", "application/json"})
    public void deleteWorld(@Context HttpServletRequest request) {
        String user = request.getHeader("X-user");
        services.deleteworld(user);
    }
}