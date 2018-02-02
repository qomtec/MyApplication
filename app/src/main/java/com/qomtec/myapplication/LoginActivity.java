package com.qomtec.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qomtec.objects.Usuario;
import com.qomtec.utils.GenerateKey;

public class LoginActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private Button btnLogin, btnSingUp,btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase db;
    private DatabaseReference fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin_login);
        btnSingUp = (Button) findViewById(R.id.btnSignUp_login);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword_login);
        txtEmail = (EditText) findViewById(R.id.txtEmail_login);
        txtPassword = (EditText) findViewById(R.id.txtPassword_login);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_login);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this, PrincipalActivity.class));
            finish();
        }
        btnSingUp.setOnClickListener(view -> {
            startActivity(new Intent(this,SignupActivity.class));
        });
        btnLogin.setOnClickListener(view -> {
            final String email = txtEmail.getText().toString().trim();
            final String password = GenerateKey.getMD5(txtPassword.getText().toString().trim());
            Usuario usuario = new Usuario();
            usuario.setUsuario(email);
            usuario.setClave(password);
            usuario.setToken(GenerateKey.getMD5(email));
            if (TextUtils.isEmpty(email)){
                Toast.makeText(LoginActivity.this, "Ingresar un email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(LoginActivity.this, "Ingresar un password", Toast.LENGTH_SHORT).show();
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            auth.signInWithEmailAndPassword(usuario.getUsuario(),usuario.getClave()).addOnCompleteListener(LoginActivity.this,(task ->{
                progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()){
                    if (password.length()<6){
                        txtPassword.setError(getString(R.string.minimum_password));
                    } else {
                        Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                    }
                } else {
                    System.out.println("funciona");
                    startActivity(new Intent(LoginActivity.this,PrincipalActivity.class));
                    finish();
                }
            } ));

            /*db = FirebaseDatabase.getInstance();
            DatabaseReference tbl = db.getReference("/tbl_usuario");
            fila = tbl.child(GenerateKey.getMD5(email).substring(0,20));
            fila.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    if (email.equals(usuario.getUsuario())){
                        if (password.equals(usuario.getClave())){
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Email inválido", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });*/
        });
    }
}
