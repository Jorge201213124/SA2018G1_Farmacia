/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import org.json.simple.JSONObject;

/**
 *
 * @author Jorge-Proyectos
 */
public class Medicamento {
    
    private int idMedicamento;
    private String Nombre;
    private String Descripcion;
    private String Fabricante;
    private double Precio;
    private int Existencias;
    private int Bajo_prescripcion;

    public Medicamento(int aInt, String string, String string0, String string1, double aDouble, int aInt0, int aInt1) {
        this.idMedicamento = aInt;
        this.Nombre = string;
        this.Fabricante = string0;
        this.Descripcion = string1;
        this.Precio = aDouble;
        this.Existencias = aInt0;
        this.Bajo_prescripcion = aInt1;
    }
    
    public Medicamento(JSONObject obj){
        this.idMedicamento = 0;
        this.Nombre = (String)obj.get("Nombre");
        this.Fabricante = (String)obj.get("Fabricante");
        this.Descripcion = (String)obj.get("Descripcion");
        this.Precio = (Double)obj.get("Precio");
        Long temp = (Long)obj.get("Existencias");
        this.Existencias = temp != null ? temp.intValue() : 0;
        temp = (Long)obj.get("Bajo_prescripcion");
        this.Bajo_prescripcion = temp != null ? temp.intValue() : 0;
    }
   
    public Medicamento(){
        
    }

    public int getIdMedicamento() {
        return idMedicamento;
    }

    public void setIdMedicamento(int idMedicamento) {
        this.idMedicamento = idMedicamento;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public String getFabricante() {
        return Fabricante;
    }

    public void setFabricante(String Fabricante) {
        this.Fabricante = Fabricante;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    public int getExistencias() {
        return Existencias;
    }

    public void setExistencias(int Existencias) {
        this.Existencias = Existencias;
    }

    public int getBajo_prescripcion() {
        return Bajo_prescripcion;
    }

    public void setBajo_prescripcion(int Bajo_prescripcion) {
        this.Bajo_prescripcion = Bajo_prescripcion;
    }

    
}
