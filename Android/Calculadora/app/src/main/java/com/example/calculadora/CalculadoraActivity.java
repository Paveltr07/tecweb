package com.example.calculadora;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CalculadoraActivity extends AppCompatActivity {
    TextView display;
    String current = "";
    String operador = "";
    double resultado = 0;
    boolean isOperadorPresionado = false;
    boolean isIgualPresionado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);

        display = findViewById(R.id.display);

        View.OnClickListener numberListener = v -> {
            Button b = (Button) v;
            String text = b.getText().toString();

            if (isIgualPresionado) {
                current = "";
                resultado = 0;
                operador = "";
                isIgualPresionado = false;
            }

            if (text.equals(".")) {
                if (current.isEmpty()) {
                    current = "0.";
                } else if (!current.contains(".")) {
                    current += ".";
                }
            } else {
                current += text;
            }

            display.setText(current);
        };

        int[] numberIDs = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        };

        for (int id : numberIDs) {
            findViewById(id).setOnClickListener(numberListener);
        }

        // Operadores
        findViewById(R.id.btnAdd).setOnClickListener(v -> operar("+"));
        findViewById(R.id.btnMul).setOnClickListener(v -> operar("*"));
        findViewById(R.id.btnDiv).setOnClickListener(v -> operar("/"));

        findViewById(R.id.btnSub).setOnClickListener(v -> {
            // Permitir ingresar un número negativo si es el primer número o después de un operador
            if (current.isEmpty() || current.equals("0") || current.equals("-")) {
                current = "-";
                display.setText(current);
            } else {
                operar("-");
            }
        });

        // Clear
        findViewById(R.id.btnClear).setOnClickListener(v -> {
            current = "";
            operador = "";
            resultado = 0;
            isOperadorPresionado = false;
            isIgualPresionado = false;
            display.setText("0");
        });

        // Igual
        findViewById(R.id.btnEqual).setOnClickListener(v -> {
            if (!current.isEmpty() && !operador.isEmpty() && !current.equals("-")) {
                double segundo = Double.parseDouble(current);

                switch (operador) {
                    case "+": resultado += segundo; break;
                    case "-": resultado -= segundo; break;
                    case "*": resultado *= segundo; break;
                    case "/":
                        if (segundo != 0) {
                            resultado /= segundo;
                        } else {
                            display.setText("Error");
                            return;
                        }
                        break;
                }

                mostrarResultado(resultado);
                operador = "";
                current = "";
                isOperadorPresionado = false;
                isIgualPresionado = true;
            }
        });
    }

    private void operar(String op) {
        if (!current.isEmpty() && !current.equals("-")) {
            double numero = Double.parseDouble(current);

            if (!isOperadorPresionado) {
                resultado = numero;
            } else {
                switch (operador) {
                    case "+": resultado += numero; break;
                    case "-": resultado -= numero; break;
                    case "*": resultado *= numero; break;
                    case "/":
                        if (numero != 0) {
                            resultado /= numero;
                        } else {
                            display.setText("Error");
                            current = "";
                            operador = "";
                            isOperadorPresionado = false;
                            return;
                        }
                        break;
                }
                mostrarResultado(resultado);
            }

            operador = op;
            current = "";
            isOperadorPresionado = true;
            isIgualPresionado = false;

        } else if (isIgualPresionado) {
            operador = op;
            isIgualPresionado = false;
        }
    }

    private void mostrarResultado(double valor) {
        if (valor == (long) valor) {
            display.setText(String.format("%d", (long) valor));
        } else {
            display.setText(String.format("%.6f", valor));
        }
    }
}
