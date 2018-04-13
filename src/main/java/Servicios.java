
import DataBase.Coneccion;
import Logica.Farmacia;
import Logica.Medicamento;
import Logica.Traslado;
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
    @Path("/traslado/validar")
    public String validateTraslado(String traslado){
        String result = "{\"resultado\":0}";
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(traslado);
            Coneccion con = new Coneccion();
            con.Conectar();
            Medicamento[] tempMed = con.getMedicamento((String)obj.get("medicamento"));
            if(tempMed.length>0){
                int actual = tempMed[0].getExistencias();
                Long solicitado = (Long)obj.get("cantidad");
                if(actual>=solicitado.intValue())
                    result = "{\"resultado\":1}";
            }
            con.Desconectar();
        } catch (ParseException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            result = "{\"error\":{\"id\":1,\"descripcion\":\"Entrada no esta en formato JSON.\"}}";
        }
        return result;
    }
    
    @POST
    @Path("/medicamento")
    public String setMedicamento(@QueryParam("nuevos") String nuevos){
        String result = "";
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(nuevos);
            JSONArray array = (JSONArray)obj.get("medicamentos");
            JSONObject iterador;
            Medicamento medTemp;
            int tam = array.size();
            Coneccion con = new Coneccion();
            con.Conectar();
            int afectados = 0;
            for(int i = 0; i< tam; i++){
                //Obtener Medicamento
                iterador = (JSONObject)array.get(i);
                medTemp = new Medicamento(iterador);
                afectados += con.insertMedicamento(medTemp);
            }
            result = "{resultado:\""+afectados+"\"}";
            con.Desconectar();
        } catch (ParseException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            result = "{\"error\":{\"id\":1,\"descripcion\":\"Entrada no esta en formato JSON.\"}}";
        }
        return result;
    }
    
    @GET
    @Path("/medicamento")
    public String existMedicamento(@QueryParam("nombre") String nombre){
        String result = "{\"resultado\":\"0\"}";
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(nombre);
            String cadena = (String)obj.get("Nombre");
            Medicamento medTemp;
            Coneccion con = new Coneccion();
            con.Conectar();
            Medicamento[] arreglo= con.getMedicamento(cadena);
            if(arreglo.length>0)
                result = "{\"resultado\":\""+arreglo[0].getIdMedicamento()+"\"}";
            con.Desconectar();
        } catch (ParseException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            result = "{\"error\":{\"id\":1,\"descripcion\":\"Entrada no esta en formato JSON.\"}}";
        }
        return result;
    }
    
    @POST
    @Path("/traslado")
    public String insertTraslado(String entrada){
        String result = "{\"resultado\":0}";
        entrada = entrada.replace("\r\n","");
        entrada = entrada.replace("\t", "");
        entrada = entrada.replace(" ","t");
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(entrada);
            JSONArray array = (JSONArray)obj.get("traslados");
            JSONObject iterador;
            Medicamento[] medTemp;
            Farmacia[] farmTemp;
            Traslado trasTemp;
            int tam = array.size();
            Coneccion con = new Coneccion();
            con.Conectar();
            int contador = 0;
            for(int i = 0; i< tam; i++){
                iterador = (JSONObject)array.get(i);
                //Obtener Medicamento
                medTemp = con.getMedicamento((String)iterador.get("medicamento"));
                //Obtener Farmacia de Destino
                farmTemp = con.getFarmacia((String)iterador.get("farmacia"));
                //Registrar traslado
                if(farmTemp.length>0 && medTemp.length>0){
                    trasTemp = new Traslado(medTemp[0],farmTemp[0],(Long)iterador.get("cantidad"),1);
                    if(trasTemp.getMedicamento().getExistencias()>=trasTemp.getCantidad()){
                        trasTemp.getFechaActual();
                        trasTemp.CambiarExistencias();
                        contador += con.insertTraslado(trasTemp);
                    }
                }
            }
            con.Desconectar();
            result = "{\"resultado\":"+contador+"}";
        } catch (ParseException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            result = "{\"error\":{\"id\":1,\"descripcion\":\"Entrada no esta en formato JSON.\"}}";
        }
        return result;
    }

    @GET
    @Path("/traslado")
    public String getTraslado(){
        String result = "{\r\n\t\"traslados\":[\r\n";
        Coneccion con = new Coneccion();
        con.Conectar();
        Traslado[] trasTemp = con.getTraslado();
        int tam = trasTemp.length;
        for(int i = 0; i<tam;i++){
            result += "\t\t{\r\n";
            result += "\t\t\t\"id\":"+trasTemp[i].getIdTRASLADO()+",\r\n";
            result += "\t\t\t\"medicamento\":\""+trasTemp[i].getMedicamento().getNombre()+"\",\r\n";
            if(trasTemp[i].getTipo()==1){
                result += "\t\t\t\"origen\":\"G1Farmacia\",\r\n";
                result += "\t\t\t\"destino\":\""+trasTemp[i].getFarmacia().getNombre()+"\",\r\n";
            }
            else{
                result += "\t\t\t\"origen\":\""+trasTemp[i].getFarmacia().getNombre()+"\",\r\n";
                result += "\t\t\t\"destino\":\"G1Farmacia\",\r\n";
            }
            result += "\t\t\t\"fecha\":\""+trasTemp[i].getFecha()+"\",\r\n";
            result += "\t\t\t\"cantidad\":"+trasTemp[i].getCantidad()+"\t\n";
            result += "\t\t},\r\n";
        }
        result = result.substring(0, result.length()-3);
        result += "\r\n\t]\r\n}";
        con.Desconectar();
        return result;
    }
}
