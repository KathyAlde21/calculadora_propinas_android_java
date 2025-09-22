package com.example.calculadorapropinas;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Declaración de variables para los elementos de la interfaz
    private TextView tvTitulo;
    private EditText etMonto;
    private EditText etPorcentaje;
    private Button btnCalcularPropina;
    private TextView tvResultado;
    private ImageView ivEstado;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable runnableLimpiarCampos;
    private boolean calculoRealizado = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflar la vista desde el archivo XML
        setContentView(R.layout.activity_main);

        //Inicialización de las vistas vinculándolas con los elementos del layout vía sus IDs
        tvTitulo = findViewById(R.id.tv_titulo);
        etMonto = findViewById(R.id.et_monto);
        etPorcentaje = findViewById(R.id.et_porcentaje);
        btnCalcularPropina = findViewById(R.id.btn_calcular_propina);
        tvResultado = findViewById(R.id.tv_resultado);
        ivEstado = findViewById(R.id.iv_estado);

        // Configuración del botón para calcular la propina al hacer clic
        btnCalcularPropina.setOnClickListener(v -> {
            if (calculoRealizado) {
            // Si ya se hizo un cálculo, limpiar campos
            limpiarCampos();
            calculoRealizado = false;
            btnCalcularPropina.setText("Calcular Propina"); // Opcional: Cambiar texto del botón
        } else {
            // Si es el primer clic, calcular propina
            calcularPropina();
        }
    });
        // Inicializar el Runnable para limpiar campos
        runnableLimpiarCampos = () -> {
            limpiarCampos();
            calculoRealizado = false; // Restablecer para el próximo cálculo
            btnCalcularPropina.setText("Calcular Propina"); // Opcional: Cambiar texto del botón
        };
    }


    /* ---------- Metodo para calcular la propina ------------------------------- */
    private void calcularPropina() {
        // Obtener el texto ingresado en los campos de monto y porcentaje en nuevas variables
        String montoTexto = etMonto.getText().toString();
        String porcentajeTexto = etPorcentaje.getText().toString();

        // Verificar si los campos de monto y porcentaje están vacíos
        if (montoTexto.isEmpty() || porcentajeTexto.isEmpty()) {
            // Toast es como un mensaje emergente, SHORT es que sea corto y show es para mostrar
            Toast.makeText(this, "Por favor, ingrese un monto y un porcentaje", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convertir a numeros enteros
        int monto = Integer.parseInt(montoTexto);
        int porcentaje = Integer.parseInt(porcentajeTexto);
        Toast.makeText(this, "Monto: " + monto + ", Porcentaje: " + porcentaje + " %", Toast.LENGTH_LONG).show();

        // Ocultar la imagen de estado
        //ivEstado.setVisibility(View.INVISIBLE);

        // Calcular la propina y total
        int propina = monto * porcentaje / 100;
        int total = monto + propina;

        // Mostrar el resultado en el TextView
        String resultado = "Propina: $" + propina + "\nTotal a pagar: $" + total;
        tvResultado.setText(resultado);

        // Cambiar imagen segun porcentaje ingresado propina
        cambiarImagen(porcentaje);

        // Marcar que se ha realizado un cálculo
        calculoRealizado = true;
        btnCalcularPropina.setText("Limpiar"); // Opcional: Cambiar texto del botón
    }

    /* ---------- Metodo para limpiar los campos -------------------------------- */
    private void limpiarCampos() {
        etMonto.setText("");
        etPorcentaje.setText("");
        tvResultado.setText("");
        ivEstado.setVisibility(View.GONE); // Ocultar la imagen al limpiar
        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show();
    }

    /* ---------- Cambiar imagen segun propina ------------------------------- */
    private void cambiarImagen(int porcentaje) {
        // Hacer visible la imagen cuando se calcule la propina
        ivEstado.setVisibility(View.VISIBLE);

        // Cambiar la imagen según el porcentaje ingresado
        if (porcentaje <= 10) {
            //propina baja => cara enojada
            ivEstado.setImageResource(R.drawable.enojado);
        } else if (porcentaje > 10 && porcentaje <= 15) {
            //propina normal => cara neutra
            ivEstado.setImageResource(R.drawable.neutro);
        } else {
            //propina alta => cara feliz
            ivEstado.setImageResource(R.drawable.feliz);
        }
    }
}