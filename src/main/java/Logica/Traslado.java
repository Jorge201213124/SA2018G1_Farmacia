/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logica;

import java.util.Calendar;

/**
 *
 * @author Jorge-Proyectos
 */
public class Traslado {

    /**
     * @return the medicamento
     */
    public Medicamento getMedicamento() {
        return medicamento;
    }

    /**
     * @param medicamento the medicamento to set
     */
    public void setMedicamento(Medicamento medicamento) {
        this.medicamento = medicamento;
    }

    /**
     * @return the farmacia
     */
    public Farmacia getFarmacia() {
        return farmacia;
    }

    /**
     * @param farmacia the farmacia to set
     */
    public void setFarmacia(Farmacia farmacia) {
        this.farmacia = farmacia;
    }

    /**
     * @return the cantidad
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the tipo
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    private int idTRASLADO;
    private Medicamento medicamento;
    private Farmacia farmacia;
    private int cantidad;
    private int tipo;
    private String fecha;
    
    public Traslado(Medicamento med, Farmacia fam, Long cant, Long type){
        this.medicamento = med;
        this.farmacia = fam;
        this.cantidad = cant.intValue();
        this.tipo = type.intValue();
    }
    
    public Traslado(){
        
    }
    
    public void getFechaActual(){
        Calendar c1 = Calendar.getInstance();
        this.fecha = c1.get(Calendar.YEAR)+"/"+c1.get(Calendar.MONTH)+"/"+c1.get(Calendar.DATE);
    }
    
    public void CambiarExistencias(){
        int actual = this.medicamento.getExistencias();
        if(tipo == 1)
            this.medicamento.setExistencias(actual - this.cantidad);
        else
            this.medicamento.setExistencias(actual + this.cantidad);
    }

    public String getFecha() {
        return fecha;
    }

    public int getIdTRASLADO() {
        return idTRASLADO;
    }

    public void setIdTRASLADO(int idTRASLADO) {
        this.idTRASLADO = idTRASLADO;
    }
    
}
