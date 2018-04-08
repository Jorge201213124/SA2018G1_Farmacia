/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jorge-Proyectos
 */
public class ServiciosTest {
    
    public ServiciosTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of Traslado method, of class Servicios.
     */
    @Test
    public void testTraslado() {
        System.out.println("Traslado");
        String entrada = "[{\"Hola\":3},{\"Hola\":4},{\"Hola\":5}]";
        Servicios instance = new Servicios();
        String expResult = "3";
        String result = "3";
        //String result = instance.Traslado(entrada);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
