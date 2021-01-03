package com.example.appsindividual.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appsindividual.Local;
import com.example.appsindividual.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Agregar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar2);

        setTitle("Agregar Establecimiento");


        Spinner spinnerComida = findViewById(R.id.spinner);

        String[] lista = {"Restaurant", "Licorería"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
        spinnerComida.setAdapter(adapter);
    }

    int validar=0 ;

    public  void guardar(View view){

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null){
            String uid = currentUser.getUid();
            String displayName = currentUser.getDisplayName();
            String email = currentUser.getEmail();

            Spinner tipo=findViewById(R.id.spinner);
            EditText ubicacion = findViewById(R.id.edtUbica);
            EditText nombre = findViewById(R.id.edtNombre);
            EditText detalles = findViewById(R.id.edtDetalles);

            Local local = new Local();

            local.setTipo(tipo.getSelectedItem().toString());
            local.setDetalle(detalles.getText().toString());
            local.setUbicacion(ubicacion.getText().toString());
            local.setNombre(nombre.getText().toString());
            local.setUidCreador(uid);
            local.setNombreCreador(displayName);
            local.setEmailCreador(email);
            local.setEstado("Pendiente");

            if(ubicacion.getText().toString().equalsIgnoreCase("")){
                ubicacion.setError("Debe ingresar la ubicación");
                Toast.makeText(this, "DEBE ESPECIFICAR UBICACIÓN", Toast.LENGTH_SHORT).show();
            }else if (nombre.getText().toString().equalsIgnoreCase("")){
                nombre.setError("Debe ingresar nombre del Local");
                Toast.makeText(this, "DEBE ESPECIFICAR NOMBRE", Toast.LENGTH_SHORT).show();
            }else if(detalles.getText().toString().equalsIgnoreCase("")){
                detalles.setError("Debe ingresar detalles del Local");
                Toast.makeText(this, "DEBE ESPECIFICAR DETALLES", Toast.LENGTH_SHORT).show();
            }else if(ubicacion.getText().toString().equalsIgnoreCase("")&&nombre.getText().toString().equalsIgnoreCase("")&&
                    detalles.getText().toString().equalsIgnoreCase("") ) {
                ubicacion.setError("Debe ingresar la ubicación");
                nombre.setError("Debe ingresar nombre del Local");
                detalles.setError("Debe ingresar detalles del Local");
                Toast.makeText(this, "DEBE RELLENAR  LAS CASILLAS", Toast.LENGTH_SHORT).show();

            }else{
                DatabaseReference dbRefStock = FirebaseDatabase.getInstance().getReference();
                dbRefStock.child("Local").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        List<String> keys = new ArrayList<>();

                        for (DataSnapshot keyNode : dataSnapshot.getChildren()) {
                            keys.add(keyNode.getKey());
                            Local localExis = keyNode.getValue(Local.class);

                            if (tipo.getSelectedItem().toString().contentEquals(localExis.getTipo())
                                    && nombre.getText().toString().contentEquals(localExis.getNombre()) ) {

                                Toast.makeText(Agregar.this, "EL ESTABLECIMIENTO YA EXISTE", Toast.LENGTH_LONG).show();
                                validar = 1;

                            }

                        }
                        if(validar!=1){
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                            databaseReference.child("Local").push().setValue(local).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Agregar.this, "Se solicitó anuncio", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(Agregar.this, Imagen.class);
                                    intent.putExtra("tipo", tipo.getSelectedItem().toString());
                                    intent.putExtra("name", nombre.getText().toString());
                                    startActivity(intent);
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    Toast.makeText(Agregar.this, "Error al guardar", Toast.LENGTH_LONG).show();
                                    e.printStackTrace();
                                }
                            });
                        }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }

    }



}