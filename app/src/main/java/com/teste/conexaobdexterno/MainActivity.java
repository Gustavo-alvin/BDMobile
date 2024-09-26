package com.teste.conexaobdexterno;

import androidx.appcompat.app.AppCompatActivity;

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
                    conexao = DriverManager.getConnection("jdbc:jtds:sqlserver://192.168.0.80;databaseName=TEEM_ANDROID;user=zelda;password=@ITB123456;");
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                return conexao;

            }

            private void inserirUsuario() {
                try {
                    PreparedStatement pst = conexaoBD().prepareStatement("insert into USUARIO values (?,?)");
                    String nome = edtNome.getText().toString().trim();
                    String email = edtEmail.getText().toString().trim();

                    if(nome.isEmpty() || nome.equals("")){
                        Toast.makeText(getApplicationContext(), "INSIRA UM NOME", Toast.LENGTH_SHORT).show();
                        edtNome.setFocusable(true);
                    }else{
                      pst.setString(2, email);
                    }
                    pst.executeUpdate();
                    Toast.makeText(getApplicationContext(),"USUARIO INSERIDO COM SUCESSO", Toast.LENGTH_SHORT).show();
                }catch (SQLException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}