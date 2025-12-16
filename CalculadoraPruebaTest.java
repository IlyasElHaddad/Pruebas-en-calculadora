package com.mycompany.calculadoraprueba;

import javax.swing.SwingUtilities;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.netbeans.jemmy.operators.*;

public class CalculadoraPruebaTest {

    private JFrameOperator frame;
    private JTextFieldOperator pantalla;

    @BeforeEach
    public void setUp() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            new CalculadoraPrueba().setVisible(true);
        });

        frame = new JFrameOperator("Calculadora");
        pantalla = new JTextFieldOperator(frame, 0);
        pantalla.clearText();
    }

    @AfterEach
    public void tearDown() {
        frame.dispose();
    }

    @ParameterizedTest
    @CsvSource({
        "7,+,5,12",
        "9,-,4,5",
        "3,*,4,12",
        "8,/,2,3"
    })
    public void testOperacionesBasicas(String op1, String operador, String op2, String esperado) {
        new JButtonOperator(frame, op1).push();
        new JButtonOperator(frame, operador).push();
        new JButtonOperator(frame, op2).push();
        new JButtonOperator(frame, "=").push();

        Assertions.assertEquals(esperado, pantalla.getText());
    }

    @ParameterizedTest
    @CsvSource({
        "8,/,0",
        "5,/,0"
    })
    public void testDivisionEntreCero(String op1, String operador, String op2) {
        new JButtonOperator(frame, op1).push();
        new JButtonOperator(frame, operador).push();
        new JButtonOperator(frame, op2).push();
        new JButtonOperator(frame, "=").push();

        Assertions.assertEquals("Error", pantalla.getText());
    }

    @Test
    public void testOperacionCombinadaSimple() {
        new JButtonOperator(frame, "5").push();
        new JButtonOperator(frame, "+").push();
        new JButtonOperator(frame, "3").push();
        new JButtonOperator(frame, "=").push();

        Assertions.assertEquals("8", pantalla.getText());

        new JButtonOperator(frame, "*").push();
        new JButtonOperator(frame, "2").push();
        new JButtonOperator(frame, "=").push();

        Assertions.assertEquals("16", pantalla.getText());
    }

    @Test
    public void testBotonC() {
        new JButtonOperator(frame, "9").push();
        new JButtonOperator(frame, "C").push();

        Assertions.assertEquals("", pantalla.getText());
    }
}
