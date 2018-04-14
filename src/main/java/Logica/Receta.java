/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author gojor
 */
public class Receta {
    
    private int idRECETA;
    private String fecha;
    private int DESPACHO;
    private ArrayList<DetalleReceta> list;
    
    
    public Receta(int id, String date, int desp){
        this.idRECETA = id;
        this.fecha = date;
        this.DESPACHO = desp;
    }
    
    public Receta(int id, int desp){
        this.idRECETA = id;
        this.DESPACHO = desp;
    }
    
    public void getFechaActual(){
        Calendar c1 = Calendar.getInstance();
        this.fecha = c1.get(Calendar.YEAR)+"/"+c1.get(Calendar.MONTH)+"/"+c1.get(Calendar.DATE);
    }

    public int getIdRECETA() {
        return idRECETA;
    }

    public void setIdRECETA(int idRECETA) {
        this.idRECETA = idRECETA;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getDESPACHO() {
        return DESPACHO;
    }

    public void setDESPACHO(int DESPACHO) {
        this.DESPACHO = DESPACHO;
    }

    public ArrayList<DetalleReceta> getList() {
        return list;
    }

    public void setList(ArrayList<DetalleReceta> list) {
        this.list = list;
    }
    
}
