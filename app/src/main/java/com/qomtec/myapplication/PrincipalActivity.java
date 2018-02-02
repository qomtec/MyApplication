package com.qomtec.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {
    private Button btnSalir;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        btnSalir = (Button)findViewById(R.id.btnSalir);
        auth = FirebaseAuth.getInstance();
        btnSalir.setOnClickListener(view -> {
            auth.signOut();
            finish();
        });
    }
}
