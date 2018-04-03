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
 * @author gojor
 */
public class OperacionTest {
    
    public OperacionTest() {
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
     * Test of Mul method, of class Operacion.
     */
    @Test
    public void testMul() {
        System.out.println("Mul");
        int num1 = 100;
        int num2 = 2;
        Operacion instance = new Operacion();
        String expResult = "199";
        String result = instance.Mul(num1, num2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of Sum method, of class Operacion.
     */
    @Test
    public void testSum() {
        System.out.println("Sum");
        int num1 = 100;
        int num2 = 2;
        Operacion instance = new Operacion();
        String expResult = "102";
        String result = instance.Sum(num1, num2);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
