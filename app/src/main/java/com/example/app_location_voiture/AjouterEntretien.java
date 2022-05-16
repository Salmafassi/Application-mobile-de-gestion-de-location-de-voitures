package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AjouterEntretien extends AppCompatActivity {
    DatabaseReference databaseVehicule;
    DatabaseReference databaseEntretien;
    List<Vehicule> vehicules;
    ArrayAdapter<String> adapter;
    ArrayList<String> newone;
    EditText type;
  EditText montant;
  TextView DateE;
  Spinner sp;
  TextView resultat;
  TextView numberE;
  static int idE=1;
  Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_entretien);
        type=(EditText) findViewById(R.id.type);
        vehicules=new ArrayList<>();
        sp=(Spinner)findViewById(R.id.spinner);
        numberE=(TextView)findViewById(R.id.numeroE);
        newone=new ArrayList<>();
        databaseVehicule= FirebaseDatabase.getInstance().getReference("Vehicule");
        databaseEntretien=FirebaseDatabase.getInstance().getReference("Entretien");
        final Calendar c=Calendar.getInstance();
        final int year=c.get(Calendar.YEAR);
        final int month=c.get(Calendar.MONTH);
        final int day=c.get(Calendar.DAY_OF_MONTH);
        resultat=(TextView)findViewById(R.id.resultat);
        save=(Button)findViewById(R.id.save);
        DateE=(TextView) findViewById(R.id.dateEntretien);
        newone.add("choisir un matricule");
        montant=(EditText) findViewById(R.id.montant);
        numberE.setText("Entretien N: "+idE);
        DateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AjouterEntretien.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateE.setText(year+"/"+month+"/"+dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.setTitle("Choisir une date :");
                datePickerDialog.show();
            }
        });

        databaseVehicule.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //clearing the previous artist list


                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Vehicule vehicule = postSnapshot.getValue(Vehicule.class);
                    //adding artist to the list

                    vehicules.add(vehicule);
                    newone.add(vehicule.getMatricule());

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
        adapter=new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item,newone);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricule=sp.getSelectedItem().toString();
                String DateEntr=DateE.getText().toString().trim();
                String typeE=type.getText().toString().trim();

                String montantT=montant.getText().toString().trim();
                if (!matricule.equals("choisir un matricule") && !TextUtils.isEmpty(DateEntr) && !TextUtils.isEmpty(typeE) && !TextUtils.isEmpty(montantT)) {

                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Artist
                    String id = databaseEntretien.push().getKey();

                    //creating an Artist Object
                    Entretien entretien=new Entretien(String.valueOf(idE),DateEntr,matricule,typeE, montantT);
                    entretien.setIdEntretien(id);
                    idE++;
                    //Saving the Artist
                    databaseEntretien.child(id).setValue(entretien);


                    resultat.setText("l'entretien est ajouté avec succès");
                    resultat.setBackgroundColor(Color.GREEN);
                    DateE.setText("");
                    montant.setText("");
                    numberE.setText("Entretien N: "+idE);
                    type.setText("");
                    sp.setSelection(0);
                    //displaying a success toast

                } else {
                    //if the value is not given displaying a toast
                    resultat.setText("Tous les champs sont requis");
                    resultat.setBackgroundColor(Color.RED);
                }

            }
        });
    }
}