package br.com.monkey.aula_28_05.controller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.monkey.aula_28_05.R;

public class MainActivity extends AppCompatActivity {

    private Button CadastrarCategoria, CadastrarProduto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CadastrarCategoria = findViewById(R.id.btnCadastrarCategoria);
        CadastrarProduto = findViewById(R.id.btnCadastrarProduto);

        CadastrarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, br.com.monkey.aula_28_05.viewer.CadastrarCategoria.class));
            }
        });
        CadastrarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, br.com.monkey.aula_28_05.viewer.CadastrarProduto.class));
            }
        });
    }
}