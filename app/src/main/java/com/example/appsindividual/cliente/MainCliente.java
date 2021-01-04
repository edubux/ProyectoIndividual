package com.example.appsindividual.cliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.appsindividual.Local;
import com.example.appsindividual.Login;
import com.example.appsindividual.R;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainCliente extends AppCompatActivity {
    String importanceHigh = "importanceHigh" ;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cliente);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Local").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String uid = currentUser.getUid();

                Local localCli = dataSnapshot.getValue(Local.class);
                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if(localCli.getUidCreador().equalsIgnoreCase(uid)){
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                        NotificationChannel notificationChannelHigh = new NotificationChannel( importanceHigh, "Notificaciones importance Low",
                                NotificationManager.IMPORTANCE_HIGH);

                        notificationChannelHigh.setDescription("Canal con notificaciones que solo aparecen en notification drawer");
                        notificationManager.createNotificationChannel(notificationChannelHigh);
                    }

                    String nombreNot= localCli.getNombre();
                    String tipoNot = localCli.getTipo();
                    String estadoNot=  localCli.getEstado();
                    notificationImportanceHigh(v, estadoNot, tipoNot , nombreNot );
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String displayName = currentUser.getDisplayName();
            TextView textView = findViewById(R.id.salu2);
            textView.setText("¡Bienvenido, " + displayName + "!");
        }
    }
    public void notificationImportanceHigh(View view, String estado, String tipo , String nombre ) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, importanceHigh);

        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Mikhuna");
        //Aparecerá como informacion relevante  para el cliente
        builder.setContentText("Tu solicitud " +tipo + " " + nombre + " ha sido " + estado);
    builder.setPriority(NotificationCompat.PRIORITY_HIGH);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Se envía la notificación
        notificationManager.notify(2, builder.build());
 }

    public  void  Cerrar(View view){
        AuthUI instance = AuthUI.getInstance();
        instance.signOut(this).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                startActivity(new Intent(MainCliente.this, Login.class));
                finish();
            }
        });
    }

    public void agregar(View view){

        Intent intent = new Intent(MainCliente.this, Agregar.class);

        startActivity(intent);

    }

    public void restaurantes(View view){

        Intent intent = new Intent(MainCliente.this, Restaurantes.class);

        startActivity(intent);
    }

    public  void licorerias(View view){
        Intent intent = new Intent(MainCliente.this, Licorerias.class);

        startActivity(intent);
    }

}