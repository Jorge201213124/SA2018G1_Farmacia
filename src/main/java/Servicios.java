
import DataBase.Coneccion;
import Logica.Despacho;
import Logica.DetalleReceta;
import Logica.Farmacia;
import Logica.Medicamento;
import Logica.Receta;
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
                result = "{\"resultado\":\""+arreglo[0].getIdMedicamento()+"\"a}";
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
        entrada = entrada.replace(" ","");
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
    
    @POST
    @Path("/receta")
    public String setReceta(String entrada){
        String result = "{\"result\":0}";
        entrada = entrada.replace("'","");
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(entrada);
            String dpi = (String)obj.get("dpi");
            Despacho[] despTemp;
            Coneccion con = new Coneccion();
            con.Conectar();
            despTemp = con.getDespacho(dpi);
            if(despTemp.length == 0){
                String name = (String)obj.get("nombre");
                Despacho nuevo = new Despacho(name,dpi);
                con.insertDespacho(nuevo);
                despTemp = con.getDespacho(dpi);
            }
            Long temp = (Long)obj.get("id_receta");
                Receta rec = new Receta(temp.intValue(),despTemp[0].getIdDESPACHO());
                rec.getFechaActual();
                int cant = con.insertReceta(rec);
                if(cant!=0){
                    String productos = (String)obj.get("productos");
                    productos = productos.substring(1);
                    productos = productos.substring(0, productos.length()-1);
                    String cantidades = (String)obj.get("cantidades");
                    cantidades = cantidades.substring(1);
                    cantidades = cantidades.substring(0, cantidades.length()-1);
                    String[] arrPro = productos.split(",");
                    String[] arrCant = cantidades.split(",");
                    int tam = arrPro.length;
                    Medicamento medTemp[];
                    DetalleReceta temDet;
                    int contador = 0;
                    for(int i = 0; i<tam; i++){
                        medTemp = con.getMedicamento(arrPro[i]);
                        if(medTemp.length>0){
                            temDet = new DetalleReceta(Integer.parseInt(arrCant[i]),medTemp[0]);
                            temDet.setRec(rec);
                            contador += con.insertDetalle(temDet);
                        }
                    }
                    result = "{\"result\":"+contador+"}";
                }
            con.Desconectar();
        } catch (ParseException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            result = "{\"error\":{\"id\":1,\"descripcion\":\"Entrada no esta en formato JSON.\"}}";
        }
        return result;
    }
    
    @GET
    @Path("/receta")
    public String getReceta(@QueryParam("entrada") String entrada){
        String result = "{\"Result\":0}";
        try{
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject)parser.parse(entrada);
            Long valDPI = (Long)obj.get("Dpi");
            Coneccion con = new Coneccion();
            con.Conectar();
            Despacho[] tempDesp = con.getDespacho(valDPI.toString());
            if(tempDesp.length > 0){
                result = "{\r\n\t\"Result\":1,\r\n\t\"Despacho\":[\r\n";
                //Mostrar despachos
                con.getRecetas(tempDesp[0]);
                Receta tempRec;
                DetalleReceta tempDet;
                int tam = tempDesp[0].getList().size();
                int tam2 = 0;
                for(int i = 0; i<tam; i++){
                    tempRec = tempDesp[0].getList().get(i);
                    result += "\t\t{\r\n";
                    result += "\t\t\t\"idReceta\":"+tempRec.getIdRECETA()+",\r\n";
                    result += "\t\t\t\"Medicamentos\":[\r\n";
                    con.getDetalle(tempRec);
                    tam2 = tempRec.getList().size();
                    for(int j = 0; j<tam2; j++){
                        tempDet = tempRec.getList().get(j);
                        result += "\t\t\t\t{\r\n";
                        result += "\t\t\t\t\t\"NombreMedicamento\":\""+tempDet.getMed().getNombre()+"\",\r\n";
                        result += "\t\t\t\t\t\"Fabricante\":\""+tempDet.getMed().getFabricante()+"\",\r\n";
                        result += "\t\t\t\t\t\"Precio\":"+tempDet.getMed().getPrecio()+",\r\n";
                        result += "\t\t\t\t\t\"Cantidad\":"+tempDet.getCantidad()+",\r\n";
                        result += j == tam2-1 ? "\t\t\t\t}\r\n" : "\t\t\t\t},\r\n";
                    } 
                    result += "\t\t\t]\r\n";
                    result += i == tam-1 ? "\t\t}\r\n":"\t\t}\r\n";
                }
                result += "\t]\r\n}";
            }
            con.Desconectar();
        } catch (ParseException ex) {
            Logger.getLogger(Servicios.class.getName()).log(Level.SEVERE, null, ex);
            result = "{\"error\":{\"id\":1,\"descripcion\":\"Entrada no esta en formato JSON.\"}}";
        }
        return result;
    }
}
