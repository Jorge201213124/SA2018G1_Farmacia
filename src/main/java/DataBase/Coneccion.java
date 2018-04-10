/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import Logica.Farmacia;
import Logica.Medicamento;
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
    private String cadenaConexion = "jdbc:mysql://localhost:3306/";
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
            result = (Farmacia[]) listado.toArray();
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
