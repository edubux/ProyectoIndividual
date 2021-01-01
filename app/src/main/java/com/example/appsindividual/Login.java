package com.example.appsindividual;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appsindividual.admin.MainAdmin;
import com.example.appsindividual.cliente.Cliente;
import com.example.appsindividual.cliente.MainCliente;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ValiUsu();
    }


public  void Login(View view){

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build()
    );

    AuthMethodPickerLayout authMethodPickerLayout = new AuthMethodPickerLayout.Builder(R.layout.login_eleccion)
            .setEmailButtonId(R.id.buttonCorreo)
            .setGoogleButtonId(R.id.buttonGoogle)
            .build();

    AuthUI instance = AuthUI.getInstance();
    Intent intent = instance
            .createSignInIntentBuilder()
            .setAuthMethodPickerLayout(authMethodPickerLayout)
            .setAvailableProviders(providers)
            .build();


    startActivityForResult(intent,1);
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            ValiUsu();
        }
    }

public  void ValiUsu(){
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    if(user!=null){
        user.reload();
        if(user.isEmailVerified()){


            String uid = user.getUid();

            if (user.getEmail().contentEquals("edubux98@gmail.com")){
                startActivity(new Intent(Login.this, MainAdmin.class));
                finish();
            }else{
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("clientes").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        //Log.d("infoapp",snapshot.getValue().toString());
                        if (snapshot.getValue() != null) {

                           // Cliente clienteDTO = snapshot.getValue(Cliente.class);

                          //  Log.d("infoapp","Nombre: "+clienteDTO.getNombre()+" , Rol: "+clienteDTO.getRol());

                            startActivity(new Intent(Login.this, MainCliente.class));
                            finish();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }




        }else{
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(Login.this,"Se le ha enviado un correo para verificar su cuenta.",Toast.LENGTH_LONG).show();
                }
            });



        }


    }


}
}