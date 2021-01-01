package com.example.appsindividual;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appsindividual.cliente.Cliente;
import com.example.appsindividual.cliente.MainCliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Guardar extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

public void GuardarDatos(View view){

    EditText editText = findViewById(R.id.editTextTextPersonName);
   FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    if(currentUser!=null){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Cliente clienteDTO = new Cliente();
        clienteDTO.setNombre(currentUser.getDisplayName());
        clienteDTO.setEmail(currentUser.getEmail());

        databaseReference.child("clientes").child(currentUser.getUid()).setValue(clienteDTO)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("con fe ","Guardado de usuario exitoso");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                    }
                });

        startActivity(new Intent(this, MainCliente.class));
        finish();

    }
}
}
