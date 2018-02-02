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

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.qomtec.objects.Usuario;
import com.qomtec.utils.GenerateKey;

import java.util.concurrent.CountDownLatch;

public class SignupActivity extends AppCompatActivity {
    private EditText txtEmail, txtPassword;
    private Button btnLogin, btnSingUp,btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseDatabase db;
    private DatabaseReference fila;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        btnLogin = (Button) findViewById(R.id.btnLogin_l);
        btnSingUp = (Button) findViewById(R.id.btnSignUp_l);
        btnResetPassword = (Button) findViewById(R.id.btnResetPassword_l);
        txtEmail = (EditText) findViewById(R.id.txtEmail_l);
        txtPassword = (EditText) findViewById(R.id.txtPassword_l);
        progressBar = (ProgressBar) findViewById(R.id.progressBar_l);
        auth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener((view)->{
            final String email = txtEmail.getText().toString().trim();
            final String password = GenerateKey.getMD5(txtPassword.getText().toString().trim());
            Usuario usuario = new Usuario();
            usuario.setUsuario(email);
            usuario.setClave(password);
            usuario.setToken(GenerateKey.getMD5(email));
            if (TextUtils.isEmpty(email)){
                Toast.makeText(SignupActivity.this, "Ingresar un email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(SignupActivity.this, "Ingresar un password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressBar.setVisibility(View.VISIBLE);
            auth.createUserWithEmailAndPassword(usuario.getUsuario(),usuario.getClave()).addOnCompleteListener(SignupActivity.this,(task -> {
                Toast.makeText(SignupActivity.this, "Usuario creado, operación completa!" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                if (!task.isSuccessful()){
                    Toast.makeText(SignupActivity.this, "Falló el registro " + task.getException(), Toast.LENGTH_SHORT).show();

                } else {
                    startActivity(new Intent(SignupActivity.this,LoginActivity.class));

                }
            }));
            db = FirebaseDatabase.getInstance();
            DatabaseReference tbl = db.getReference("/tbl_usuario");
            fila = tbl.child(GenerateKey.getMD5(email).substring(0,20));

            fila.setValue(usuario, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                    System.out.println("Completado correctamente");
                }
            });
        });
    }

}
