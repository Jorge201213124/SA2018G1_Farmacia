
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
@Path("multiplicacion")
public class multiplicacion {
    
    @GET
    public String Operar(@QueryParam("num1") int num1, @QueryParam("num2") int num2){
        return String.valueOf(num1*num2);
    }
}

