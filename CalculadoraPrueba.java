package com.mycompany.calculadoraprueba;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CalculadoraPrueba extends JFrame {

    private JTextField txtPantalla;
    private double numero1 = 0;
    private String operacion = "";
    private boolean reiniciar = false;

    public CalculadoraPrueba() {
        setTitle("Calculadora");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        txtPantalla = new JTextField();
        txtPantalla.setEditable(false);
        txtPantalla.setHorizontalAlignment(JTextField.RIGHT);
        txtPantalla.setFont(new Font("Arial", Font.BOLD, 24));

        JPanel panelBotones = new JPanel(new GridLayout(5, 4, 5, 5));

        String[] botones = {
            "7","8","9","+",
            "4","5","6","-",
            "1","2","3","*",
            "0","C","=","/"
        };

        for (String b : botones) {
            JButton btn = new JButton(b);
            btn.setFont(new Font("Arial", Font.BOLD, 20));
            btn.addActionListener(this::pulsarBoton);
            panelBotones.add(btn);
        }

        add(txtPantalla, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }

    private void pulsarBoton(ActionEvent e) {
        String t = e.getActionCommand();

        if (t.matches("[0-9]")) {
            if (reiniciar) {
                txtPantalla.setText("");
                reiniciar = false;
            }
            txtPantalla.setText(txtPantalla.getText() + t);

        } else if (t.matches("[+\\-*/]")) {
            numero1 = Double.parseDouble(txtPantalla.getText());
            operacion = t;
            reiniciar = true;

        } else if (t.equals("=")) {
            double numero2 = Double.parseDouble(txtPantalla.getText());
            double resultado = 0;

            switch (operacion) {
                case "+" -> resultado = numero1 + numero2;
                case "-" -> resultado = numero1 - numero2;
                case "*" -> resultado = numero1 * numero2;
                case "/" -> {
                    if (numero2 == 0) {
                        txtPantalla.setText("Error");
                        return;
                    }
                    resultado = numero1 / numero2;
                }
            }

            if (resultado == (int) resultado) {
                txtPantalla.setText(String.valueOf((int) resultado));
            } else {
                txtPantalla.setText(String.valueOf(resultado));
            }

            reiniciar = true;

        } else if (t.equals("C")) {
            txtPantalla.setText("");
            numero1 = 0;
            operacion = "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalculadoraPrueba().setVisible(true));
    }
}
