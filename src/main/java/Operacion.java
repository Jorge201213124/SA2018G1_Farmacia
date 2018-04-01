
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jorge-Proyectos
 */
@Path("/operacion/")
@Produces("application/json")
public class Operacion {
    
    @GET
    @Path("/por")
    public String Mul(@QueryParam("num1") int num1, @QueryParam("num2") int num2){
        return String.valueOf(num1*num2);
    }
    
    @POST
    @Path("/sum")
    public String Sum(@QueryParam("num1") int num1, @QueryParam("num2") int num2){
        return String.valueOf(num1+num2);
    }
}

