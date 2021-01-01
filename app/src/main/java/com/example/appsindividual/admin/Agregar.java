package com.example.appsindividual.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.appsindividual.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Agregar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        setTitle("Agregar Carta");


        Spinner spinnerComida = findViewById(R.id.spinner);

        String[] lista = {"Almuerzo", "Piqueo", "Bebida"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
        spinnerComida.setAdapter(adapter);
    }


public  void guardar(View view){

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    if (currentUser != null){
        String uid = currentUser.getUid();
        String displayName = currentUser.getDisplayName();
        Spinner agregar=findViewById(R.id.spinner);
        EditText tipo = findViewById(R.id.edtTipo);
        EditText nombre = findViewById(R.id.edtNombre);
        EditText precio = findViewById(R.id.edtPrecio);


    }

}



}