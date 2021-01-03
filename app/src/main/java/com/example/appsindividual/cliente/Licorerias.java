package com.example.appsindividual.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.example.appsindividual.Local;
import com.example.appsindividual.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Licorerias extends AppCompatActivity {
    List<Local> localList = new ArrayList<>();
    Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_licorerias);
        setTitle("Licorerías");


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    Local locales = children.getValue(Local.class);
                    if (locales.getEstado().equalsIgnoreCase("Aprobado")&&locales.getTipo().equalsIgnoreCase("Licorería") ){

                        localList.add(new Local(locales.getNombre(),locales.getTipo(),locales.getUbicacion(),locales.getDetalle()));

                    }
                }


                AdapterCliente adapterCliente= new AdapterCliente(localList,Licorerias.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyLicoreria);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Licorerias.this));
                recyclerView.setAdapter(adapterCliente);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}