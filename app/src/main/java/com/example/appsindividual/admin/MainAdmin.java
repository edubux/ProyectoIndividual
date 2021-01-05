package com.example.appsindividual.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appsindividual.Login;
import com.example.appsindividual.R;
import com.example.appsindividual.cliente.Agregar;
import com.example.appsindividual.cliente.MainCliente;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainAdmin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uid = currentUser.getUid();
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();


            TextView textView = findViewById(R.id.salu2Admin);
            textView.setText("¡Bienvenido, " + displayName );
        }
    }



    public  void  CerrarAdmin(View view){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(MainAdmin.this, Login.class));
                finish();
            }
        });
    }

    public void anuncios( View view ){

        Intent intent = new Intent(MainAdmin.this, AnunciosNew.class);

        startActivity(intent);

    }

    public  void gestion(View view){
        Intent intent = new Intent(MainAdmin.this, Gestion.class);

        startActivity(intent);

    }
}