/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

/**
 *
 * @author gojor
 */
public class DetalleReceta {
    
    private int Cantidad;
    private Medicamento med;
    
    public DetalleReceta(int cant, Medicamento m){
        this.Cantidad = cant;
        this.med = m;
    }

    public int getCantidad() {
        return Cantidad;
    }

    public void setCantidad(int Cantidad) {
        this.Cantidad = Cantidad;
    }

    public Medicamento getMed() {
        return med;
    }

    public void setMed(Medicamento med) {
        this.med = med;
    }
    
}
