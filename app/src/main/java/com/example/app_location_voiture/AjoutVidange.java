package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class AjoutVidange extends AppCompatActivity {
    DatabaseReference databaseVehicule;
    DatabaseReference datavidange;
    List<Vehicule> vehicules;
  ArrayAdapter<String> adapter;
    ArrayList<String> newone;
    Spinner sp;
    Spinner sp1;
    TextView dateV;
    TextView resultat;
    EditText montant;
    TextView kmA;
    TextView kmP;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajout_vidange);
        vehicules=new ArrayList<>();
        sp=(Spinner)findViewById(R.id.spinner);
        newone=new ArrayList<>();
        sp1=(Spinner)findViewById(R.id.spinner1);
        datavidange=FirebaseDatabase.getInstance().getReference("Vidange");
        newone.add("choisir un matricule");
        databaseVehicule= FirebaseDatabase.getInstance().getReference("Vehicule");
        final Calendar c=Calendar.getInstance();
        final int year=c.get(Calendar.YEAR);
        final int month=c.get(Calendar.MONTH);
        final int day=c.get(Calendar.DAY_OF_MONTH);
        resultat=(TextView)findViewById(R.id.resultat);
        save=(Button)findViewById(R.id.save);
        dateV=(TextView) findViewById(R.id.dateDebut);
        montant=(EditText) findViewById(R.id.montant);
        kmA=(TextView) findViewById(R.id.kmA);
        kmP=(TextView) findViewById(R.id.kmP);
        dateV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AjoutVidange.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateV.setText(year+"/"+month+"/"+dayOfMonth);
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

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                databaseVehicule.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            Vehicule vehicule = postSnapshot.getValue(Vehicule.class);
                            //adding artist to the list
                           if(vehicule.getMatricule().equals(newone.get(position))){
                               kmA.setText(vehicule.getKm());

                           }


                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }

                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int kmActual= Integer.valueOf(sp.getSelectedItem().toString());
                int valeur= Integer.parseInt(sp1.getSelectedItem().toString());
                int resullt=kmActual+valeur;
                kmP.setText(resultat.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}