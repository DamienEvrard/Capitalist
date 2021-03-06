package ISIS_Capitalist.WebService;

import ISIS_Capitalist.tools.AngelUpgradeService;
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
public class WebServiceAngelUpgrade {
    private AngelUpgradeService services;

    public WebServiceAngelUpgrade() {
        this.services = new AngelUpgradeService();
    }
    
    @PUT
    @Path("angelupgrade")
    @Consumes({"application/xml","application/json"})
    public void updateAngelUpgrade(@Context HttpServletRequest request,@RequestBody PallierType newAngelUpgrade) throws JAXBException, FileNotFoundException{
        String user = request.getHeader("X-user");
        services.updateAngelUpgrade(newAngelUpgrade,user);
    }
}
 