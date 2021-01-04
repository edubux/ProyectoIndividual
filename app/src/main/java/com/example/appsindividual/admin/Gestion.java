package com.example.appsindividual.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appsindividual.Local;
import com.example.appsindividual.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

public class Gestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        List<Local> localList = new ArrayList<>();
        Activity activity = this;
        setTitle("Gestión");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                localList.clear();
                List<String> keys = new ArrayList<>();
                for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                    keys.add(keyNode.getKey());
                    Local locals = keyNode.getValue(Local.class);
                    if (locals.getEstado().equalsIgnoreCase("Pendiente") ) {
                        localList.add(new Local(locals.getNombre(),locals.getTipo(),locals.getUbicacion(),locals.getDetalle(),
                                locals.getNombreCreador(),locals.getUidCreador(),locals.getEmailCreador(),locals.getEstado()));
                    }
                }
                if (localList.isEmpty()){
                    TextView vacio= findViewById(R.id.vacioGestion);
                    vacio.setText("No hay solicitudes");
                    vacio.setVisibility(View.VISIBLE);
                }else{
                    //Se le integra al Adaptador
                    AdapterAdmin2 adapterAdmin2= new AdapterAdmin2(localList, Gestion.this,activity);
                    RecyclerView recyclerView = findViewById(R.id.RecyGestion);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Gestion.this));
                    recyclerView.setAdapter(adapterAdmin2);


                    adapterAdmin2.setOnItemClickListener(new AdapterAdmin2.OnItemClickListener() {
                        @Override
                        public void onRechazarClick(int position) {
                            Local localSel = new Local();
                            localSel.setNombre(localList.get(position).getNombre());
                            localSel.setTipo(localList.get(position).getTipo());
                            localSel.setUbicacion(localList.get(position).getUbicacion());
                            localSel.setDetalle(localList.get(position).getDetalle());
                            localSel.setNombreCreador(localList.get(position).getNombreCreador());
                            localSel.setUidCreador(localList.get(position).getUidCreador());
                            localSel.setEmailCreador(localList.get(position).getEmailCreador());
                            // localSel.setEstado(localList.get(position).getEstado());



                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                            databaseRef.child("Local").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    List<String> keys = new ArrayList<>();
                                    for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                                        keys.add(keyNode.getKey());
                                        Local localAp = keyNode.getValue(Local.class);
                                        if(localAp.getNombre().equalsIgnoreCase(localSel.getNombre())&&
                                                localAp.getTipo().equalsIgnoreCase(localSel.getTipo())){
                                            localSel.setEstado("Rechazado");
                                            DatabaseReference dbRefUpdate = FirebaseDatabase.getInstance().getReference();
                                            dbRefUpdate.child("Local").child(keyNode.getKey()).setValue(localSel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Gestion.this,"Solicitud Rechazada",Toast.LENGTH_LONG).show();
                                                    //notificationImportanceLow(v);
                                                    finish();
                                                    startActivity(new Intent(Gestion.this, Gestion.class));

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("infoapp","Error en el rechazo");
                                                    Toast.makeText(Gestion.this,"Error en el rechazo",Toast.LENGTH_LONG).show();
                                                    e.printStackTrace();
                                                }
                                            });
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


                        }

                        @Override
                        public void onAprobarClick(int position) {
                            Local localSel = new Local();
                            localSel.setNombre(localList.get(position).getNombre());
                            localSel.setTipo(localList.get(position).getTipo());
                            localSel.setUbicacion(localList.get(position).getUbicacion());
                            localSel.setDetalle(localList.get(position).getDetalle());
                            localSel.setNombreCreador(localList.get(position).getNombreCreador());
                            localSel.setUidCreador(localList.get(position).getUidCreador());
                            localSel.setEmailCreador(localList.get(position).getEmailCreador());
                            // localSel.setEstado(localList.get(position).getEstado());



                            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
                            databaseRef.child("Local").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    List<String> keys = new ArrayList<>();
                                    for(DataSnapshot keyNode : dataSnapshot.getChildren()){
                                        keys.add(keyNode.getKey());
                                        Local localAp = keyNode.getValue(Local.class);
                                        if(localAp.getNombre().equalsIgnoreCase(localSel.getNombre())&&
                                                localAp.getTipo().equalsIgnoreCase(localSel.getTipo())){
                                            localSel.setEstado("Aprobado");
                                            DatabaseReference dbRefUpdate = FirebaseDatabase.getInstance().getReference();
                                            dbRefUpdate.child("Local").child(keyNode.getKey()).setValue(localSel).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(Gestion.this,"Solicitud Aprobada",Toast.LENGTH_LONG).show();
                                                    //notificationImportanceLow(v);
                                                    finish();
                                                    startActivity(new Intent(Gestion.this, Gestion.class));

                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("infoapp","Error en la aprobación");
                                                    Toast.makeText(Gestion.this,"Error en la aprobación",Toast.LENGTH_LONG).show();
                                                    e.printStackTrace();
                                                }
                                            });
                                        }

                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
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