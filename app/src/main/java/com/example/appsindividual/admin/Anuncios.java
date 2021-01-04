package com.example.appsindividual.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.appsindividual.Local;
import com.example.appsindividual.R;
import com.example.appsindividual.cliente.AdapterCliente;
import com.example.appsindividual.cliente.MainCliente;
import com.example.appsindividual.cliente.Restaurantes;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Anuncios extends AppCompatActivity {
    String tipoString;
    List<Local> localList = new ArrayList<>();
    Activity activity = this;
    TextView titulo= findViewById(R.id.titulo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);
        setTitle("Anuncios");

            //  Spinner spinnerTipo = findViewById(R.id.SpinnerTipo);

        String[] lista = {"Todas","Restaurant", "Licorería"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, lista);
     //   spinnerTipo.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                localList.clear();
                List<String> keys = new ArrayList<>();
              /*  Spinner tipo=findViewById(R.id.spinner);
                if(tipo != null){
                     tipoString = tipo.getSelectedItem().toString();
                }else{
                     tipoString="Todas";
                }*/


                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Local locals = keyNode.getValue(Local.class);



                   /* if (locals.getEstado().equalsIgnoreCase("Pendiente")&&locals.getTipo().equalsIgnoreCase(tipoString) ){

                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle(),locals.getTipo()));

                    }else*/ if (locals.getEstado().equalsIgnoreCase("Aprobado") ) {
                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle()));
                    }

                }
                AdapterAdmin adapterAdmin= new AdapterAdmin(localList, Anuncios.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Anuncios.this));
                recyclerView.setAdapter(adapterAdmin);

                titulo.setText("Todos");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle()));
                    }

                }
                AdapterAdmin adapterAdmin= new AdapterAdmin(localList, Anuncios.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Anuncios.this));
                recyclerView.setAdapter(adapterAdmin);
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
                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle()));
                    }

                }
                AdapterAdmin adapterAdmin= new AdapterAdmin(localList, Anuncios.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Anuncios.this));
                recyclerView.setAdapter(adapterAdmin);
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
                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle()));
                    }

                }
                AdapterAdmin adapterAdmin= new AdapterAdmin(localList, Anuncios.this,activity);
                RecyclerView recyclerView = findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(Anuncios.this));
                recyclerView.setAdapter(adapterAdmin);
                titulo.setText("Todos");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}