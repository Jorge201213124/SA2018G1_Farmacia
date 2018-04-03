/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

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
public class ConeccionTest {
    
    public ConeccionTest() {
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
     * Test of Conectar method, of class Coneccion.
     */
    @Test
    public void testConectar() {
        System.out.println("Conectar");
        Coneccion instance = new Coneccion();
        instance.Conectar();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of Prueba method, of class Coneccion.
     */
    @Test
    public void testPrueba() {
        System.out.println("Prueba");
        Coneccion instance = new Coneccion();
        int expResult = 1;
        int result = instance.Prueba();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
