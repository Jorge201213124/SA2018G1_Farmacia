/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import DataBase.Coneccion;

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
        this.Precio = aDouble;
        this.Existencias = aInt0;
        this.Bajo_prescripcion = aInt1;
    }
   

    
}
