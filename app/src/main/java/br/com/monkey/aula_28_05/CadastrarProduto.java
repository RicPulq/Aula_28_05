package br.com.monkey.aula_28_05;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.monkey.aula_28_05.model.Categoria;
import br.com.monkey.aula_28_05.model.Produto;

public class CadastrarProduto extends AppCompatActivity {

    private EditText edtNomeProduto;
    private Spinner spinnerCategoria;
    private Button btnSalvarProduto;
    private DatabaseReference databaseReference; // Referência para o banco de dados do Firebase
    private List<Categoria> categoriasList; // Lista de categorias

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_produto); // Define o layout da atividade

        // Inicialização dos componentes da interface
        edtNomeProduto = findViewById(R.id.edtNomeProduto);
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnSalvarProduto = findViewById(R.id.btnSalvarProduto);

        // Inicializar a referência do Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference();

        categoriasList = new ArrayList<>(); // Inicializa a lista de categorias
        carregarCategorias(); // Chama o método para carregar as categorias

        // Define a ação do botão salvar
        btnSalvarProduto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvarProduto(); // Chama o método para salvar o produto
            }
        });
    }

    // Método para carregar as categorias do Firebase
    private void carregarCategorias() {
        databaseReference.child("categorias").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                categoriasList.clear(); // Limpa a lista de categorias
                for (DataSnapshot categoriaSnapshot : snapshot.getChildren()) {
                    Categoria categoria = categoriaSnapshot.getValue(Categoria.class); // Converte o snapshot em um objeto Categoria
                    categoriasList.add(categoria); // Adiciona a categoria à lista
                }
                List<String> categoriaNomes = new ArrayList<>();
                for (Categoria categoria : categoriasList) {
                    categoriaNomes.add(categoria.getNome()); // Adiciona o nome da categoria à lista de nomes
                }
                // Configura o adaptador para o spinner com os nomes das categorias
                ArrayAdapter<String> adapter = new ArrayAdapter<>(CadastrarProduto.this, android.R.layout.simple_spinner_item, categoriaNomes);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategoria.setAdapter(adapter); // Define o adaptador no spinner
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Exibe uma mensagem de erro caso a carga das categorias falhe
                Toast.makeText(CadastrarProduto.this, "Erro ao carregar categorias!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para salvar o produto no Firebase
    private void salvarProduto() {
        String nomeProduto = edtNomeProduto.getText().toString().trim(); // Obtém o nome do produto
        int categoriaIndex = spinnerCategoria.getSelectedItemPosition(); // Obtém o índice da categoria selecionada

        // Verifica se o nome do produto não está vazio e se uma categoria foi selecionada
        if (!nomeProduto.isEmpty() && categoriaIndex != -1) {
            Categoria categoriaSelecionada = categoriasList.get(categoriaIndex); // Obtém a categoria selecionada
            String id = databaseReference.child("produtos").push().getKey(); // Gera um novo ID para o produto
            Produto produto = new Produto(null, nomeProduto, categoriaSelecionada.getNome()); // Cria um objeto Produto
            databaseReference.child("produtos").child("Produto" + (categoriaIndex + 1)).setValue(produto); // Salva o produto no Firebase

            // Exibe uma mensagem de sucesso
            Toast.makeText(this, "Produto salvo!", Toast.LENGTH_SHORT).show();
            finish(); // Encerra a atividade
        } else {
            // Exibe uma mensagem de erro caso os campos não estejam preenchidos
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
        }
    }
}



