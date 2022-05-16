package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AfficherVehicules extends AppCompatActivity {
 TextView mark;
 TextView model;
 ImageView image;
    TextView prixj;
    TextView prixM;
    TextView kelometrage;
    TextView matricule;
    TextView etat;
    TextView puissance;
    String idVehicule;
    String extension;
    Button update,delete;
    Uri mImageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_vehicules);
        mark=(TextView) findViewById(R.id.mark);
        model=(TextView) findViewById(R.id.modele);
        image=(ImageView) findViewById(R.id.car);
        prixj=(TextView) findViewById(R.id.prixjV);
        prixM=(TextView) findViewById(R.id.prixM);
        kelometrage=(TextView) findViewById(R.id.kmV);
        matricule=(TextView) findViewById(R.id.matricule);
        etat=(TextView)findViewById(R.id.etat);
        puissance=(TextView) findViewById(R.id.puissance);
        update=(Button)findViewById(R.id.update);
        delete=(Button)findViewById(R.id.delete);
        Intent intent = getIntent();
        mark.setText(intent.getStringExtra("mark"));
        model.setText(intent.getStringExtra("model"));
        prixj.setText(intent.getStringExtra("PrixJ"));
        prixM.setText(intent.getStringExtra("PrixM"));
        idVehicule=intent.getStringExtra("idVehicule");
        kelometrage.setText(intent.getStringExtra("kmA"));
        matricule.setText(intent.getStringExtra("matricule"));
        etat.setText(intent.getStringExtra("etat"));
        puissance.setText(intent.getStringExtra("puissance"));
        extension=intent.getStringExtra("extension");


        String affichage=mark.getText().toString();
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDialog(intent,affichage);
            }


        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new AlertDialog.Builder(AfficherVehicules.this)
                        .setTitle("Voulez-vous supprimer: " +mark.getText().toString()+ " ?")
                        .setNegativeButton("Annuler", null)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Vehicule").child(idVehicule);
                                dR.removeValue();
                                Intent intent = new Intent(getApplicationContext(), list_Vehicule.class);
                                startActivity(intent);
                            }
                        })
                        .create();
                d.setOwnerActivity(AfficherVehicules.this);
                d.show();


            }
        });
        StorageReference databaseStorage= FirebaseStorage.getInstance().getReference("imagesVehicule").child(idVehicule+"."+extension);
        databaseStorage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downUri = task.getResult();
                    String imageUrl = downUri.toString();

                    Picasso.get().load(imageUrl).resize(360,200).into(image);

                }else{

                }
            }
        });

        //starting the activity with intent


    }
    private void showUpdateDialog(Intent intent, String mark) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_vehicule, null);
        dialogBuilder.setView(dialogView);
        DatabaseReference databaseVehicule = FirebaseDatabase.getInstance().getReference("Vehicule");



       Button choisImag=(Button)dialogView.findViewById(R.id.choisImag);
        EditText matricule2=(EditText) dialogView.findViewById(R.id.matricul);


       EditText couleur2=(EditText) dialogView.findViewById(R.id.color);

        Spinner etat2=(Spinner)dialogView.findViewById(R.id.etat);
       EditText KM2=(EditText) dialogView.findViewById(R.id.km);
        EditText kmM2=(EditText) dialogView.findViewById(R.id.kmM);
        EditText kmJ2=(EditText) dialogView.findViewById(R.id.kmj);
        Button save = (Button) dialogView.findViewById(R.id.save1);
        TextView resultat1 = (TextView) dialogView.findViewById(R.id.resultatv);
        Button annuler = (Button) dialogView.findViewById(R.id.annuler1);
        dialogBuilder.setTitle(mark);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        StorageReference databaseStorage= FirebaseStorage.getInstance().getReference("imagesVehicule");

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
                String matricule1=matricule2.getText().toString();

                String color1=couleur2.getText().toString();

                String etat1=etat2.getSelectedItem().toString();
                String KM1=KM2.getText().toString();
                String kmM1=kmM2.getText().toString();
                String kmJ1=kmJ2.getText().toString();
                if(!TextUtils.isEmpty(matricule1) &&  !TextUtils.isEmpty(color1) && !TextUtils.isEmpty(KM1) && !TextUtils.isEmpty(kmM1) && !TextUtils.isEmpty(kmJ1) ){


                    if(mImageUri!=null){

                        Vehicule vehicule=new Vehicule( idVehicule,matricule1, mark, model.getText().toString(), color1, puissance.getText().toString(), KM1, kmJ1, kmM1,etat1,String.valueOf(mImageUri));
                        vehicule.setExtensionImage(getFileExtension(mImageUri));
                        StorageReference fileReference=databaseStorage.child(idVehicule+"."+getFileExtension(mImageUri));

                        fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                                databaseVehicule.child(idVehicule).setValue(vehicule);
                                b.dismiss();
                                Toast.makeText(AfficherVehicules.this, "la vehicule est modifiée avec succès", Toast.LENGTH_LONG).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AfficherVehicules.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                        StorageReference databaseStorage1= FirebaseStorage.getInstance().getReference("imagesVehicule").child(idVehicule+"."+extension);
                        databaseStorage1.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                if (task.isSuccessful()) {
                                    Uri downUri = task.getResult();
                                    String imageUrl = downUri.toString();

                                    Picasso.get().load(imageUrl).resize(360,200).into(image);

                                }else{

                                }
                            }
                        });
                        matricule.setText(matricule1);


                        etat.setText(etat1);
                        kelometrage.setText(KM1);
                        prixM.setText(kmM1);
                        prixj.setText(kmJ1);

                    }
                } else {
                    //if the value is not given displaying a toast
                    resultat1.setText("Tous les champs sont requis");
                    resultat1.setBackgroundColor(Color.RED);
                }
            }
        });
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();

            }
        });


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