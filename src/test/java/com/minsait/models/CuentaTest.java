package com.minsait.models;

import com.minsait.exceptions.DineroInsuficienteException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class CuentaTest {

    Cuenta cuenta;
    TestInfo testInfo;

    TestReporter testReporter;

//DRY
    @BeforeEach
    void setUp(TestInfo testInfo, TestReporter testReporter) {
        this.cuenta = new Cuenta("Ricardo", new BigDecimal(1000));
        testReporter.publishEntry("Iniciando el metodo");
        this.testInfo=testInfo;
        this.testReporter=testReporter;
        testReporter.publishEntry("Ejecutanto " + testInfo.getTestMethod().get().getName());
    }

    @AfterEach
    void tearDown() {
        System.out.println("Finalizando el metodo de prueba");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Iniciando todos los tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("Finalizando todos los test");
    }



    @Nested
    class CuentaNombreSaldo{

        @Test
        void testNombreCuenta(){
            var esperado="Ricardo";
            var real=cuenta.getPersona();
            assertNotNull(real);
            assertEquals(esperado, real);
            //assertTrue(esperado.equals(real));
        }

        @Test
        void testSaldoCuenta(){
            assertFalse(cuenta.getSaldo().compareTo(BigDecimal.ZERO)<0);
            assertEquals(1000, cuenta.getSaldo().intValue());
        }

        @Test
        void testReferencia(){
            Cuenta cuenta2 = new Cuenta("Ricardo", new BigDecimal(1000));
            assertEquals(cuenta2,cuenta);

        }
    }


    @Nested
    class CuentaOperacionesTest{
        @Test
        void testRetirarCuenta(){
            cuenta.retirar((new BigDecimal(100)));

            assertNotNull(cuenta.getSaldo());
            assertEquals("900", cuenta.getSaldo().toPlainString());
        }

        @Test
        void  testDepositarCuenta(){
            cuenta.despositar(new BigDecimal(100));

            assertNotNull(cuenta.getSaldo());
            assertEquals("1100",cuenta.getSaldo().toPlainString());
        }


        @Test
        void testTransferirEntreCuentas(){
            Cuenta cuenta3 = new Cuenta("Eder", new BigDecimal(50000));

            Banco banco= new Banco();

            banco.setNombre("BBVA");
            banco.transferir(cuenta3, cuenta, new BigDecimal("5000"));

            assertEquals("45000", cuenta3.getSaldo().toPlainString());
            assertEquals("6000", cuenta.getSaldo().toPlainString());
        }
    }


    @Test
    void testException(){
        Exception exception=assertThrows(DineroInsuficienteException.class,
                ()->cuenta.retirar( new BigDecimal(1001)));

        assertEquals("Dinero Insuficiente", exception.getMessage());

        assertEquals(DineroInsuficienteException.class, exception.getClass());

        assertTrue(cuenta.getSaldo().compareTo(BigDecimal.ZERO)>0);

    }


    @Test
    void testRelacionBancoCuentas(){
        Cuenta cuenta2 = new Cuenta("Eder", new BigDecimal(50000));
        Banco banco = new Banco();
        banco.setNombre("BBVA");
        banco.addCuenta(cuenta);
        banco.addCuenta(cuenta2);
        banco.transferir(cuenta2, cuenta, new BigDecimal(5000));

        assertAll(

                ()->assertEquals("45000", cuenta2.getSaldo().toPlainString()),
                ()->assertEquals("6000", cuenta.getSaldo().toPlainString()),
                ()->assertEquals("BBVA", cuenta.getBanco().getNombre()),
                //()->assertEquals(cuenta2.getPersona(), banco.getCuentas().stream().filter(cuenta -> cuenta.getPersona().equals(cuenta2.getPersona())).findFirst().get().getPersona()),
                ()->assertTrue(banco.getCuentas().stream().anyMatch(cuenta -> cuenta.getPersona().equals(cuenta2.getPersona())))
                //()->assertThrows(DineroInsuficienteException.class, ()->banco.transferir(cuenta2, cuenta, new BigDecimal("-50000")))




        );

    }
}