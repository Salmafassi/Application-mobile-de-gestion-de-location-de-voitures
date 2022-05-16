package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AjouterVidange extends AppCompatActivity {
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
    TextView vidangeN;

    static int idV=1;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_vidange);
        vehicules=new ArrayList<>();
        sp=(Spinner)findViewById(R.id.spinner);
        newone=new ArrayList<>();
        vidangeN=(TextView)findViewById(R.id.vidangeNumer) ;
        sp1=(Spinner)findViewById(R.id.spinner1);
        datavidange= FirebaseDatabase.getInstance().getReference("Vidange");
        newone.add("choisir un matricule");
        databaseVehicule= FirebaseDatabase.getInstance().getReference("Vehicule");
        final Calendar c=Calendar.getInstance();
        final int year=c.get(Calendar.YEAR);
        final int month=c.get(Calendar.MONTH);
        final int day=c.get(Calendar.DAY_OF_MONTH);
        resultat=(TextView)findViewById(R.id.resultat);
        save=(Button)findViewById(R.id.save);
        dateV=(TextView) findViewById(R.id.dateVidang);
        montant=(EditText) findViewById(R.id.montantV);
        kmA=(TextView) findViewById(R.id.kmaV);
        kmP=(TextView) findViewById(R.id.kmPV);
        vidangeN.setText("Vidange N: "+(AjouterVidange.idV));
        dateV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AjouterVidange.this, new DatePickerDialog.OnDateSetListener() {
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
                                int kmActual=Integer.parseInt(kmA.getText().toString());
                                int valeur= Integer.parseInt(sp1.getSelectedItem().toString());
                                int result=kmActual+valeur;
                                kmP.setText(String.valueOf(result));

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
                int kmActual=Integer.parseInt(kmA.getText().toString());
                int valeur= Integer.parseInt(sp1.getSelectedItem().toString());
                int result=kmActual+valeur;
               kmP.setText(String.valueOf(result));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matricule=sp.getSelectedItem().toString();

                String DateV= dateV.getText().toString().trim();
                String typeV=sp1.getSelectedItem().toString();
                String montantT=montant.getText().toString().trim();
                String kmpro=kmP.getText().toString().trim();
                if (!matricule.equals("choisir un matricule") && !TextUtils.isEmpty(DateV) && !TextUtils.isEmpty(typeV) && !TextUtils.isEmpty(montantT)) {

                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Artist
                    String id = datavidange.push().getKey();


                    //creating an Artist Object
                    Vidange vidange=new Vidange(String.valueOf(idV),matricule, DateV,montantT,typeV,kmpro,id);



                    AjouterVidange.idV++;
                    datavidange.child(id).setValue(vidange);


                    resultat.setText("la vidange est ajoutée avec succès");
                    resultat.setBackgroundColor(Color.GREEN);
                    dateV.setText("");
                   kmA.setText("0");
                    montant.setText("");
                    vidangeN.setText("Vidange N: "+idV);
                    kmP.setText(sp1.getSelectedItem().toString());
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