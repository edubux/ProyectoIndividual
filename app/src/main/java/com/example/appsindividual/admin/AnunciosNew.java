package com.example.appsindividual.admin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appsindividual.Local;
import com.example.appsindividual.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class AnunciosNew extends AppCompatActivity {

    List<Local> localList = new ArrayList<>();
    Activity activity = this;
    TextView titulo;
    View v;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        setTitle("Anuncios");

        todas(v);

    }

    public void filterRest(View view ){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                localList.clear();
                List<String> keys = new ArrayList<>();



                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Local locals = keyNode.getValue(Local.class);
                    if (locals.getEstado().equalsIgnoreCase("Aprobado") && locals.getTipo().equalsIgnoreCase("Restaurant") ) {
                        String archivo = locals.getNombre() + locals.getTipo();
                        StorageReference reference = FirebaseStorage.getInstance().getReference().child(archivo).child("imagen");

                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle(),reference));
                    }

                }
                AdapterAdmin adapterAdmin= new AdapterAdmin(localList, AnunciosNew.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(AnunciosNew.this));
                recyclerView.setAdapter(adapterAdmin);
                titulo= findViewById(R.id.tituloAnun);
               titulo.setText("Restaurantes");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void filLico(View view){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                localList.clear();
                List<String> keys = new ArrayList<>();



                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Local locals = keyNode.getValue(Local.class);
                    if (locals.getEstado().equalsIgnoreCase("Aprobado") && locals.getTipo().equalsIgnoreCase("Licorería")  ) {
                        String archivo = locals.getNombre() + locals.getTipo();
                        StorageReference reference = FirebaseStorage.getInstance().getReference().child(archivo).child("imagen");

                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle(),reference));
                    }

                }
                AdapterAdmin adapterAdmin= new AdapterAdmin(localList, AnunciosNew.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(AnunciosNew.this));
                recyclerView.setAdapter(adapterAdmin);
                titulo= findViewById(R.id.tituloAnun);
               titulo.setText("Licorerías");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public  void todas(View view){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                localList.clear();
                List<String> keys = new ArrayList<>();



                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Local locals = keyNode.getValue(Local.class);
                    if (locals.getEstado().equalsIgnoreCase("Aprobado") ) {
                        String archivo = locals.getNombre() + locals.getTipo();
                        StorageReference reference = FirebaseStorage.getInstance().getReference().child(archivo).child("imagen");

                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle(),reference));
                    }

                }
                AdapterAdmin adapterAdmin= new AdapterAdmin(localList, AnunciosNew.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(AnunciosNew.this));
                recyclerView.setAdapter(adapterAdmin);
                titulo= findViewById(R.id.tituloAnun);
                titulo.setText("Todos");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
