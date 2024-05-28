package br.com.monkey.aula_28_05;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.monkey.aula_28_05.R;
import br.com.monkey.aula_28_05.model.Categoria;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CadastrarCategoria extends AppCompatActivity {
    private EditText edtNomeCategoria;
    private Button btnSalvarCategoria;
    private DatabaseReference databaseReference; // Referência para o banco de dados do Firebase

    private int categoriaIndex; // Variável para armazenar o índice atual das categorias

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_categoria); // Define o layout da atividade

        // Inicialização dos componentes da interface
        edtNomeCategoria = findViewById(R.id.edtNomeCategoria);
        btnSalvarCategoria = findViewById(R.id.btnSalvarCategoria);

        // Inicializar a referência do Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("categorias");

        // Obtém o índice atual de categorias do Firebase
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // Atualiza a variável categoriaIndex com o número de categorias existentes
                categoriaIndex = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Tratar possíveis erros ao acessar o Firebase
            }
        });

        // Define a ação do botão salvar
        btnSalvarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvarCategoria(); // Chama o método para salvar a categoria
            }
        });
    }

    // Método para salvar a categoria no Firebase
    private void salvarCategoria() {
        // Obtém o nome da categoria do campo de entrada de texto
        String nomeCategoria = edtNomeCategoria.getText().toString().trim();

        // Verifica se o nome da categoria não está vazio
        if (!nomeCategoria.isEmpty()) {
            // Incrementa o índice da categoria para criar uma nova categoria
            final int index = categoriaIndex + 1;
            // Cria um objeto Categoria com o índice e nome fornecidos
            Categoria categoria = new Categoria("Categoria" + index, nomeCategoria);
            // Salva a categoria no Firebase usando o índice como chave
            databaseReference.child("Categoria" + index).setValue(categoria);

            // Exibe uma mensagem de sucesso
            Toast.makeText(this, "Categoria salva!", Toast.LENGTH_SHORT).show();
            finish(); // Encerra a atividade
        } else {
            // Exibe uma mensagem de erro caso o nome da categoria esteja vazio
            Toast.makeText(this, "Digite o nome da categoria!", Toast.LENGTH_SHORT).show();
        }
    }
}
