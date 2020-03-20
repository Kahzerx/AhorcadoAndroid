package com.example.ahorcado;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int numeroDeFallos = 0;
    int numeroDeAciertos = 0;
    String palabraOculta = "";
    boolean gameOver = false;
    ArrayList<View> pressedButtons = new ArrayList<View>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.ventanaJuego, new VentanaAhorcado()).commit();
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        palabraOculta = word();
        String huecos = "";
        for (int i = 0; i < palabraOculta.length(); i++) huecos += "_ ";

        ((TextView) findViewById(R.id.palabraConGuiones)).setText(huecos.trim());

        findViewById(R.id.resetButton).setVisibility(View.INVISIBLE);
    }

    private String word(){
        String[] wordList = {"CETYS", "TEST", "HOLA", "ADIOS", "VLADIKAKA", "BORREGUITO", "BABYYODA"};
        Random rand = new Random();
        return wordList[rand.nextInt(wordList.length)];
    }

    public void botonPulsado (View vista){
        if (!gameOver) {
            pressedButtons.add(findViewById(vista.getId()));
            Button boton = findViewById(vista.getId());
            boton.setVisibility(View.INVISIBLE);
            chequeaLetra(boton.getText().toString());

        }
    }

    public void reset (View vista){
        if (gameOver) {
            numeroDeFallos = 0;
            numeroDeAciertos = 0;
            gameOver = false;

            Button boton = findViewById(vista.getId());
            boton.setVisibility(View.INVISIBLE);

            for (View botones : pressedButtons){
                botones.setVisibility(View.VISIBLE);
            }

            pressedButtons.clear();

            ImageView imagenAhorcado = ((ImageView) findViewById(R.id.imagenAhorcado));
            imagenAhorcado.setImageResource(R.drawable.ahorcado_0);

            palabraOculta = word();
            String huecos = "";
            for (int i = 0; i < palabraOculta.length(); i++) huecos += "_ ";

            ((TextView) findViewById(R.id.palabraConGuiones)).setText(huecos.trim());
        }

    }

    private void chequeaLetra(String letra){
        letra = letra.toUpperCase();
        ImageView imagenAhorcado = ((ImageView) findViewById(R.id.imagenAhorcado));
        TextView textoConGuiones = ((TextView) findViewById(R.id.palabraConGuiones));
        String palabraConGuiones = textoConGuiones.getText().toString();

        boolean acierto = false;

        for (int i=0; i<palabraOculta.length(); i++){
            if (palabraOculta.charAt(i) == letra.charAt(0)){
                //quita el guión bajo de la letra correspondiente
                palabraConGuiones = palabraConGuiones.substring(0, 2*i)
                        + letra
                        + palabraConGuiones.substring(2*i+1);
                acierto = true;
                numeroDeAciertos++;
                if (numeroDeAciertos == palabraOculta.length()){
                    gameOver = true;
                    findViewById(R.id.resetButton).setVisibility(View.VISIBLE);
                }
            }
        }
        if (!palabraConGuiones.contains("_")){
            imagenAhorcado.setImageResource(R.drawable.acertastetodo);
        }

        textoConGuiones.setText(palabraConGuiones);

        if (!acierto){
            numeroDeFallos++;
            switch (numeroDeFallos){
                case 0 : imagenAhorcado.setImageResource(R.drawable.ahorcado_0); break;
                case 1 : imagenAhorcado.setImageResource(R.drawable.ahorcado_1); break;
                case 2 : imagenAhorcado.setImageResource(R.drawable.ahorcado_2); break;
                case 3 : imagenAhorcado.setImageResource(R.drawable.ahorcado_3); break;
                case 4 : imagenAhorcado.setImageResource(R.drawable.ahorcado_4); break;
                case 5 : imagenAhorcado.setImageResource(R.drawable.ahorcado_5); break;
                default : imagenAhorcado.setImageResource(R.drawable.ahorcado_fin); break;
            }
            if (numeroDeFallos > 5){
                gameOver = true;
                findViewById(R.id.resetButton).setVisibility(View.VISIBLE);
            }
        }

    }
}
