/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author Jorge-Proyectos
 */
public class Farmacia {

    public int getIdFarmacia() {
        return idFarmacia;
    }

    public void setIdFarmacia(int idFarmacia) {
        this.idFarmacia = idFarmacia;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }
    
    private int idFarmacia;
    private String Nombre;
    private String Direccion;

    public Farmacia(int aInt, String string, String string0) {
        this.idFarmacia = aInt;
        this.Nombre = string;
        this.Direccion = string0;
    }
    
    
}
