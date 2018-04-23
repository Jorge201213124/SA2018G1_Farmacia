/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import Logica.Despacho;
import Logica.DetalleReceta;
import Logica.Farmacia;
import Logica.Medicamento;
import Logica.Receta;
import Logica.Traslado;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jorge-Proyectos
 */
public class Coneccion {
    
    private String nameDB = "sa_farmacia";
    private String user = "sagrupo1";
    private String password = "sagrupo1";
    private String cadenaConexion = "jdbc:mysql://54.186.54.147:3306/";
    private Connection con;
    
    public void Conectar(){
        try {
            
            con = DriverManager.getConnection(cadenaConexion+nameDB, user,password);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void Desconectar(){
        if(con!=null)
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    public int insertMedicamento(Medicamento med){
        if(med != null){
            try {
                String cmd = "INSERT INTO MEDICAMENTO"
                + "(Nombre,Fabricante,Descripcion,Precio,Existencias,Bajo_prescripcion) VALUES"
                + "(?,?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(cmd);
                preparedStatement.setString(1, med.getNombre());
                preparedStatement.setString(2, med.getFabricante());
                preparedStatement.setString(3, med.getDescripcion());
                preparedStatement.setDouble(4, med.getPrecio());
                preparedStatement.setInt(5, med.getExistencias());
                preparedStatement.setInt(6, med.getBajo_prescripcion());
                int result = preparedStatement.executeUpdate();
                preparedStatement.close();
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
        return 0;
    }
    
    public Farmacia[] getFarmacia(int ide){
        Farmacia[] result = null;
        try{
            ArrayList<Farmacia> listado = new ArrayList<Farmacia>();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select * from FARMACIA where idFARMACIA = "+ide);
            Farmacia temporal;
            while (rs.next())
            {
                temporal = new Farmacia(rs.getInt(1),rs.getString(2),rs.getString(3));
                listado.add(temporal);
            }
            result = listado.toArray(new Farmacia[listado.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Farmacia[] getFarmacia(String nombre){
        Farmacia[] result = null;
        try{
            ArrayList<Farmacia> listado = new ArrayList<Farmacia>();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select * from FARMACIA where Nombre = '"+nombre+"'");
            Farmacia temporal;
            while (rs.next())
            {
                temporal = new Farmacia(rs.getInt(1),rs.getString(2),rs.getString(3));
                listado.add(temporal);
            }
            result = listado.toArray(new Farmacia[listado.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Medicamento[] getMedicamento(int ide){
        Medicamento[] result = null;
        try {
            ArrayList<Medicamento> listado = new ArrayList<Medicamento>();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select * from MEDICAMENTO where idMEDICAMENTO = "+ide);
            Medicamento temporal;
            while (rs.next())
            {
                temporal = new Medicamento(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)
                    ,rs.getDouble(5),rs.getInt(6),rs.getInt(7));
                listado.add(temporal);
            }
            result = listado.toArray(new Medicamento[listado.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Medicamento[] getMedicamento(String nombre){
        Medicamento[] result = null;
        try {
            ArrayList<Medicamento> listado = new ArrayList<Medicamento>();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select * from MEDICAMENTO where Nombre = '"+nombre+"'");
            Medicamento temporal;
            while (rs.next())
            {
                temporal = new Medicamento(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)
                    ,rs.getDouble(5),rs.getInt(6),rs.getInt(7));
                listado.add(temporal);
            }
            result = listado.toArray(new Medicamento[listado.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public Traslado[] getTraslado(){
        Traslado[] result = null;
        try {
            ArrayList<Traslado> listado = new ArrayList<Traslado>();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select * from TRASLADO_MEDICAMENTO ");
            Traslado temporal;
            while (rs.next())
            {
                temporal = new Traslado();
                temporal.setIdTRASLADO(rs.getInt("idTRASLADO_MEDICAMENTO"));
                temporal.setFecha(rs.getString(2));
                int medicamento = rs.getInt(3);
                temporal.setMedicamento(getMedicamento(medicamento)[0]);
                int origen = rs.getInt(4);
                int destino = rs.getInt(5);
                if(origen == 1){
                    temporal.setTipo(1);
                    temporal.setFarmacia(getFarmacia(destino)[0]);
                }
                else{
                    temporal.setTipo(2);
                    temporal.setFarmacia(getFarmacia(origen)[0]);
                }
                temporal.setCantidad(rs.getInt(6));
                listado.add(temporal);
            }
            result = listado.toArray(new Traslado[listado.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int UpdateMedicamento(Medicamento med){
        if(med != null){
            try {
                String cmd = "UPDATE MEDICAMENTO SET Nombre = ?, Descripcion = ?, Fabricante = ?, "
                + "Precio = ?, Existencias = ?, Bajo_prescripcion = ? where idMEDICAMENTO = ? ";
                PreparedStatement preparedStatement = con.prepareStatement(cmd);
                preparedStatement.setString(1, med.getNombre());
                preparedStatement.setString(2, med.getDescripcion());
                preparedStatement.setString(3, med.getFabricante());
                preparedStatement.setDouble(4, med.getPrecio());
                preparedStatement.setInt(5, med.getExistencias());
                preparedStatement.setInt(6, med.getBajo_prescripcion());
                preparedStatement.setInt(7, med.getIdMedicamento());
                int result = preparedStatement.executeUpdate();
                preparedStatement.close();
                //Actualizar la cantidad de medicina
                
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
        return 0;
    }
    
    public int insertDetalle(DetalleReceta det){
        if(det != null){
            try {
                String cmd = "INSERT INTO DETALLE_RECETA"
                + "(Cantidad,MEDICAMENTO,RECETA) VALUES"
                + "(?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(cmd);
                preparedStatement.setInt(1, det.getCantidad());
                preparedStatement.setInt(2, det.getMed().getIdMedicamento());
                preparedStatement.setInt(3,det.getRec().getIdRECETA());
                int result = preparedStatement.executeUpdate();
                preparedStatement.close();
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public int insertDespacho(Despacho det){
        if(det != null){
            try {
                String cmd = "INSERT INTO DESPACHO"
                + "(Nombre,DPI) VALUES"
                + "(?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(cmd);
                preparedStatement.setString(1, det.getNombre());
                preparedStatement.setString(2, det.getDpi());
                int result = preparedStatement.executeUpdate();
                preparedStatement.close();
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public int insertReceta(Receta rec){
        if(rec != null){
            try {
                String cmd = "INSERT INTO RECETA"
                + "(idRECETA,Fecha,DESPACHO) VALUES"
                + "(?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(cmd);
                preparedStatement.setInt(1, rec.getIdRECETA());
                preparedStatement.setString(2, rec.getFecha());
                preparedStatement.setInt(3, rec.getDESPACHO());
                int result = preparedStatement.executeUpdate();
                preparedStatement.close();
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }
    
    public int insertTraslado(Traslado tras){
        if(tras != null){
            try {
                String cmd = "INSERT INTO TRASLADO_MEDICAMENTO"
                + "(Fecha,MEDICAMENTO,Origen,Destino,Cantidad) VALUES"
                + "(?,?,?,?,?)";
                PreparedStatement preparedStatement = con.prepareStatement(cmd);
                preparedStatement.setString(1, tras.getFecha());
                preparedStatement.setInt(2, tras.getMedicamento().getIdMedicamento());
                if(tras.getTipo() == 1){
                    preparedStatement.setInt(3, 1);
                    preparedStatement.setInt(4, tras.getFarmacia().getIdFarmacia());
                }
                else{
                    preparedStatement.setInt(3, tras.getFarmacia().getIdFarmacia());
                    preparedStatement.setInt(4, 1);
                }
                preparedStatement.setInt(5, tras.getCantidad());
                int result = preparedStatement.executeUpdate();
                preparedStatement.close();
                //Actualizar la cantidad de medicina
                UpdateMedicamento(tras.getMedicamento());
                return result;
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
                return -1;
            }
        }
        return 0;
    }
    
    public void getDetalle(Receta rec){
        if(rec!=null){
            try {
                ArrayList<DetalleReceta> listado = new ArrayList<DetalleReceta>();
                Statement s = con.createStatement();
                ResultSet rs = s.executeQuery ("select * from DETALLE_RECETA where RECETA = "+rec.getIdRECETA()+"");
                DetalleReceta temporal;
                int ideMed;
                while (rs.next())
                {
                    ideMed = rs.getInt(2);
                    temporal = new DetalleReceta(rs.getInt(1),getMedicamento(ideMed)[0]);
                    listado.add(temporal);
                }
                rec.setList(listado);
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void getRecetas(Despacho desp){
        if(desp!=null){
            try {
            ArrayList<Receta> listado = new ArrayList<Receta>();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select * from RECETA where DESPACHO = "+desp.getIdDESPACHO()+"");
            Receta temporal;
            while (rs.next())
            {
                temporal = new Receta(rs.getInt(1),rs.getString(2),rs.getInt(3));
                listado.add(temporal);
            }
            desp.setList(listado);
            } catch (SQLException ex) {
                Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public Despacho[] getDespacho(String dpi){
        Despacho[] result = null;
        try {
            ArrayList<Despacho> listado = new ArrayList<Despacho>();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select * from DESPACHO where DPI = '"+dpi+"'");
            Despacho temporal;
            while (rs.next())
            {
                temporal = new Despacho(rs.getInt(1),rs.getString(2),rs.getString(3));
                listado.add(temporal);
            }
            result = listado.toArray(new Despacho[listado.size()]);
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public int Prueba(){
        int result = -1;
        Conectar();
        if(con!=null) result = 1;
        else result = 0;
        /*
        Statement s = con.createStatement();
        ResultSet rs = s.executeQuery ("select count(*) from FARMACIA");
        while (rs.next())
        {
        result = rs.getInt(1);
        }
        con.close();
        */
        return result;
    }
}
