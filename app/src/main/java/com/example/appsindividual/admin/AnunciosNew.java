package com.example.appsindividual.admin;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

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
    Context context;

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

                titulo= findViewById(R.id.tituloAnun);
                titulo.setText("Restaurantes");
                adapterAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnunciosNew.this);
                        alertDialog.setTitle("Eliminar Anuncio");
                        alertDialog.setMessage("Seleccionó anuncio con el" +"\nNombre: "+ localList.get(recyclerView.getChildAdapterPosition(v))
                                .getNombre() + "\nTipo: "+ localList.get(recyclerView.getChildAdapterPosition(v)).getTipo());
                        alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                eliminar(localList.get(recyclerView.getChildAdapterPosition(v))
                                        .getNombre(),localList.get(recyclerView.getChildAdapterPosition(v)).getTipo());
                                finish();
                                startActivity(getIntent());
                                Toast.makeText(activity, "Se eliminó" , Toast.LENGTH_SHORT).show();

                            }
                        });alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    }
                });
                recyclerView.setAdapter(adapterAdmin);


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
                titulo= findViewById(R.id.tituloAnun);
               titulo.setText("Licorerías");
                adapterAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnunciosNew.this);
                        alertDialog.setTitle("Eliminar Anuncio");
                        alertDialog.setMessage("Seleccionó anuncio con el" +"\nNombre: "+ localList.get(recyclerView.getChildAdapterPosition(v))
                                .getNombre() + "\nTipo: "+ localList.get(recyclerView.getChildAdapterPosition(v)).getTipo());
                        alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                eliminar(localList.get(recyclerView.getChildAdapterPosition(v))
                                        .getNombre(),localList.get(recyclerView.getChildAdapterPosition(v)).getTipo());
                                finish();
                                startActivity(getIntent());
                                Toast.makeText(activity, "Se eliminó" , Toast.LENGTH_SHORT).show();

                            }
                        });alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    }
                });

                recyclerView.setAdapter(adapterAdmin);
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
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.RecyAnuncios);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(AnunciosNew.this));

                titulo= findViewById(R.id.tituloAnun);
                titulo.setText("Todos");

                adapterAdmin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(AnunciosNew.this);
                        alertDialog.setTitle("Eliminar Anuncio");
                        alertDialog.setMessage("Seleccionó anuncio con el" +"\nNombre: "+ localList.get(recyclerView.getChildAdapterPosition(v))
                        .getNombre() + "\nTipo: "+ localList.get(recyclerView.getChildAdapterPosition(v)).getTipo());
                        alertDialog.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                eliminar(localList.get(recyclerView.getChildAdapterPosition(v))
                                        .getNombre(),localList.get(recyclerView.getChildAdapterPosition(v)).getTipo());
                                finish();
                                startActivity(getIntent());
                                Toast.makeText(activity, "Se eliminó" , Toast.LENGTH_SHORT).show();

                            }
                        });alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();

                            }
                        });
                        alertDialog.show();
                    }
                });
                recyclerView.setAdapter(adapterAdmin);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

public  void eliminar(String name, String tipo){
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
databaseReference.child("Local").addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        for (DataSnapshot children : dataSnapshot.getChildren()){
                Local local = children.getValue(Local.class);
        if(local.getNombre().equals(name)&&local.getTipo().equals(tipo)){
            databaseReference.child("Local").child(children.getKey()).child("estado").setValue("Eliminado");
        }
     }
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

}

}
