package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AjouterVehicule extends AppCompatActivity {
    Button choisImag;
     EditText matricule;
     EditText mark;
     EditText model;
     EditText couleur;
     EditText puissance;
     Spinner etat;
     EditText KM;
     EditText kmJ,kmM;
     TextView resultat;
     Button save;
    Uri mImageUri;
    DatabaseReference databaseVehicule;
    StorageReference databaseStorage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_vehicule);
        choisImag=(Button)findViewById(R.id.choisImag);
        matricule=(EditText) findViewById(R.id.matricul);
        resultat=(TextView) findViewById(R.id.resultat);
        mark=(EditText)findViewById(R.id.marque) ;
        model=(EditText)findViewById(R.id.model);
        couleur=(EditText) findViewById(R.id.color);
        puissance=(EditText)findViewById(R.id.puissance);
        etat=(Spinner)findViewById(R.id.etat);
        KM=(EditText) findViewById(R.id.km);
        kmM=(EditText) findViewById(R.id.kmM);
        kmJ=(EditText) findViewById(R.id.kmj);
        save=(Button) findViewById(R.id.save);
        databaseStorage= FirebaseStorage.getInstance().getReference("imagesVehicule");
        databaseVehicule= FirebaseDatabase.getInstance().getReference("Vehicule");
        choisImag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,1);
            }
        });
  save.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          AddVehiculePict();
      }
  });

    }
   public void AddVehiculePict(){
        String matricule1=matricule.getText().toString();
        String mark1=mark.getText().toString();
        String model1=model.getText().toString();
        String color1=couleur.getText().toString();
       String puissance1=puissance.getText().toString();
       String etat1=etat.getSelectedItem().toString();
      String KM1=KM.getText().toString();
      String kmM1=kmM.getText().toString();
      String kmJ1=kmJ.getText().toString();
      if(!TextUtils.isEmpty(matricule1) && !TextUtils.isEmpty(mark1) && !TextUtils.isEmpty(model1) && !TextUtils.isEmpty(color1) && !TextUtils.isEmpty(puissance1) && !TextUtils.isEmpty(KM1) && !TextUtils.isEmpty(kmM1) && !TextUtils.isEmpty(kmJ1) ){


          if(mImageUri!=null){
              String id = databaseVehicule.push().getKey();
              Vehicule vehicule=new Vehicule( id,matricule1, mark1, model1, color1, puissance1, KM1, kmJ1, kmM1,etat1,String.valueOf(mImageUri));
              vehicule.setExtensionImage(getFileExtension(mImageUri));
              StorageReference fileReference=databaseStorage.child(id+"."+getFileExtension(mImageUri));
              fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                  @Override
                  public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                      vehicule.idVehicule=id;
                      databaseVehicule.child(id).setValue(vehicule);

                      resultat.setText(" Vehicule ajouté");
                      resultat.setBackgroundColor(Color.GREEN);
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(AjouterVehicule.this, e.getMessage(), Toast.LENGTH_LONG).show();
                  }
              });

              matricule.setText("");
              mark.setText("");
              model.setText("");
              couleur.setText("");
              puissance.setText("");
              KM.setText("");
              kmM.setText("");
              kmJ.setText("");

          }
      } else {
          //if the value is not given displaying a toast
          resultat.setText("Tous les champs sont requis");
          resultat.setBackgroundColor(Color.RED);
      }

   }
   private String getFileExtension(Uri uri){
       ContentResolver cR=getContentResolver();
       MimeTypeMap mime= MimeTypeMap.getSingleton();
       return mime.getExtensionFromMimeType(cR.getType(uri));
   }
    @Override
    protected void onActivityResult(int requestCode, int resultCode,  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null){
             mImageUri = data.getData();

        }
    }
}