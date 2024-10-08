package com.teste.conexaobdexterno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class MainActivity extends AppCompatActivity {

        EditText edtNome, edtEmail;
        Button btnInserirUsuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtNome = (EditText) findViewById(R.id.edtNome);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        btnInserirUsuario = (Button) findViewById(R.id.btnInserirUsuario);

        btnInserirUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inserirUsuario();
            }
            public Connection conexaoBD() {
                Connection conexao = null;
                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
                    conexao = DriverManager.getConnection("jdbc:jtds:sqlserver://bd_CharityConnect_INF3FM.mssql.somee.com;databaseName=bd_CharityConnect_INF3FM;user=gustavo;password=41943419;");
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return conexao;

            }

            private void inserirUsuario() {
                try {
                    PreparedStatement pst = conexaoBD().prepareStatement("INSERT INTO Contato (motivoContato, dataContato, pergunta, nome, sobrenome, email, telefone) VALUES (?, ?, ?,?,?,?,?)");

                    String nome = edtNome.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();

                    if(nome.isEmpty() || nome.equals("")){
                        Toast.makeText(getApplicationContext(), "INSIRA UM NOME", Toast.LENGTH_SHORT).show();
                        edtNome.setFocusable(true);
                    }else{
                      pst.setString(4, nome);
                    }


                    if(email.isEmpty() || email.equals("")){
                        Toast.makeText(getApplicationContext(), "INSIRA SEU EMAIL", Toast.LENGTH_SHORT).show();
                        edtEmail.setFocusable(true);
                    }else{
                        pst.setString(6, email);
                    }

                    pst.setString(1, "teste do motivo");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        pst.setString(2, LocalDate.now().toString());
                    }
                    pst.setString(3, "pergunta teste de celula");
                    pst.setString(5, "sobrenome do celular");
                    pst.setString(7, "telefone");

                    pst.executeUpdate();
                    Toast.makeText(getApplicationContext(),"USUARIO INSERIDO COM SUCESSO", Toast.LENGTH_SHORT).show();
                }catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}