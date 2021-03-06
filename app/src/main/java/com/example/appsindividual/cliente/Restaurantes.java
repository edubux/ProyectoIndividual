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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Restaurantes extends AppCompatActivity {
    List<Local> localList = new ArrayList<>();
    Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurantes);
        setTitle("Restaurantes");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot children : dataSnapshot.getChildren()) {
                    Local locales = children.getValue(Local.class);
                    if ((locales.getEstado().equalsIgnoreCase("Aprobado"))&&(locales.getTipo().equalsIgnoreCase("Restaurant")) ){
                        String archivo = locales.getNombre() + locales.getTipo();
                        StorageReference reference = FirebaseStorage.getInstance().getReference().child(archivo).child("imagen");
                        localList.add(new Local(locales.getNombre(),locales.getTipo(),locales.getUbicacion(),locales.getDetalle(),reference));

                    }
                }


                AdapterCliente adapterCliente= new AdapterCliente(localList,Restaurantes.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyRestuarant);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Restaurantes.this));
                recyclerView.setAdapter(adapterCliente);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}