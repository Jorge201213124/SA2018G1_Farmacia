/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    public int Prueba(){
        int result = -1;
        try {
            Conectar();
            Statement s = con.createStatement();
            ResultSet rs = s.executeQuery ("select count(*) from Farmacia");
            while (rs.next())
            {
                result = rs.getInt(1);
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(Coneccion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
}
