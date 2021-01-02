package com.example.appsindividual.cliente;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appsindividual.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;


public class Imagen extends AppCompatActivity {

    ImageView selectImage;
    Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        Button btnsubir =findViewById(R.id.buttonSubir);
        selectImage=findViewById(R.id.imageView4);
        Button btnTomarFoto= findViewById(R.id.buttonFoto);
        btnTomarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int permission = ContextCompat.checkSelfPermission(Imagen.this,
                        Manifest.permission.CAMERA);

                if (permission == PackageManager.PERMISSION_GRANTED){
                    Intent camara= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camara,0);
                }else{
                    ActivityCompat.requestPermissions(Imagen.this,
                            new String[]{Manifest.permission.CAMERA},0);
                }

            }
        });

        btnsubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            //Cancelado por el usuario
        }

        if ((resultCode == RESULT_OK) && (requestCode == 1)) {

            Intent intent = getIntent();
            String name = intent.getStringExtra("name");
            String tipo = intent.getStringExtra("tipo");

            //Procesar el resultado
            Uri uri = data.getData();//obtener el uri content

            String fileName = "imagen";

            int permission = ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE);

            if (permission == PackageManager.PERMISSION_GRANTED) {
                //Subir archivo a FireStorage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();


                storageReference.child(name + "/" + tipo).child(fileName).putFile(uri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Log.d("infoapp", "Subida exitosa");
                                Toast.makeText(Imagen.this, "Imagen Subida", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(Imagen.this, MainCliente.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("infoapp", "Error en Subida");
                        Toast.makeText(Imagen.this, "Error en Subida de Imagen", Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                });


            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

            }
        }else if (requestCode==0 && resultCode==RESULT_OK){

                int permission = ContextCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA);
                if (permission == PackageManager.PERMISSION_GRANTED) {

                    image = (Bitmap) data.getExtras().get("data");
                    selectImage.setImageBitmap(image);
                    selectImage.setVisibility(View.VISIBLE);


                }else {
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.CAMERA}, 1);

                }


                Button upload = findViewById(R.id.buttonSubir);
                upload.setVisibility(View.VISIBLE);

            }


        }





    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 1) {
                agregarImagen(null);
            }

        }else if (requestCode == 0){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent camara= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camara,0);
            }else{
                Toast.makeText(this, "Se requiere permiso para usar la cámara", Toast.LENGTH_SHORT).show();
            }

        }
    }
    public void agregarImagen(View view){

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission == PackageManager.PERMISSION_GRANTED) {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            startActivityForResult(Intent.createChooser(intent, "Choose File"), 1);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

    }

    private  void upload(){
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,100,stream);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String tipo = intent.getStringExtra("tipo");
        String fileName = "imagen";


        final String random = UUID.randomUUID().toString();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageReference.child(name + "/"+tipo).child(fileName);

        byte[] b = stream.toByteArray();
        imageRef.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUri = uri;
                    }
                });

                Intent intent = new Intent(Imagen.this, MainCliente.class);
                startActivity(intent);
                Toast.makeText(Imagen.this, "Se subió la  captura  exitosamente", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("infoapp", "Error en Subida");
                Toast.makeText(Imagen.this, "Error en Subida de Imagen", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });
    }



}