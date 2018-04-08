
import DataBase.Coneccion;
import Logica.Farmacia;
import Logica.Medicamento;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jorge-Proyectos
 */
@Path("/servicios/")
//@Produces("application/json")
public class Servicios {
    
    @POST
    @Path("/traslado")
    public String Traslado(@QueryParam("entrada") String entrada){
        String result = "";
        entrada = entrada.replace("\r\n","");
        entrada = entrada.replace("\t", "");
        entrada = entrada.replace(" ","t");
        try {
            
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(entrada);
            JSONArray array = (JSONArray)obj.get("traslados");
            JSONObject iterador;
            Medicamento medTemp;
            Farmacia origenTemp;
            Farmacia destinoTemp;
            int tam = array.size();
            Coneccion con = new Coneccion();
            con.Conectar();
            for(int i = 0; i< tam; i++){
                iterador = (JSONObject)array.get(i);
               con.getMedicamento((String)iterador.get("medicamento"));
                //if(medTemp!=null) result += iterador.get("medicamento")+",";
                
            }
            con.Desconectar();        
        } catch (ParseException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            result = "{\"error\":{\"id\":1,\"descripcion\":\"Entrada no esta en formato JSON.\"}}";
        }
        return result;
    }
}
