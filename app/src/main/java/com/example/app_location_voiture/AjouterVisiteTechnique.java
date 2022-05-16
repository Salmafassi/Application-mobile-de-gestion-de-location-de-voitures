package com.example.app_location_voiture;

import static android.widget.AdapterView.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.database.AbstractCursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AjouterVisiteTechnique extends AppCompatActivity {
    DatabaseReference databaseVisits;
    DatabaseReference nbrVisitsTechnic;
    List<Vehicule> vehicules;
    ArrayAdapter<String> adapter;
    ArrayList<String> newone;
    Spinner sp;
    TextView dateD;
    TextView dateF;
    EditText agence;
    TextView resultat;
    EditText montant;
    TextView numeroVisite;
    static int numero=1;
    Button save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ajouter_visite_technique);
        vehicules=new ArrayList<>();
        sp=(Spinner)findViewById(R.id.spinner);
        newone=new ArrayList<>();
        numeroVisite=(TextView) findViewById(R.id.numeroVisite);
        numeroVisite.setText("Visite Technique N: "+numero);
         newone.add("choisir un matricule");
        nbrVisitsTechnic= FirebaseDatabase.getInstance().getReference("VisitesTechnics");
        final Calendar   c=Calendar.getInstance();
        final int year=c.get(Calendar.YEAR);
        final int month=c.get(Calendar.MONTH);
        final int day=c.get(Calendar.DAY_OF_MONTH);
        resultat=(TextView)findViewById(R.id.resultat);
        save=(Button)findViewById(R.id.save);
        dateD=(TextView) findViewById(R.id.dateDebut);
        dateF=(TextView) findViewById(R.id.dateFin);
        agence=(EditText) findViewById(R.id.agence);
        montant=(EditText) findViewById(R.id.montant);

        dateD.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AjouterVisiteTechnique.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateD.setText(year+"/"+month+"/"+dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.setTitle("Choisir une date :");
                datePickerDialog.show();
            }
        });
        dateF.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(AjouterVisiteTechnique.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateF.setText(year+"/"+month+"/"+dayOfMonth);
                    }
                },year,month,day);
                datePickerDialog.setTitle("Choisir une date :");
                datePickerDialog.show();
            }
        });
        databaseVisits= FirebaseDatabase.getInstance().getReference("Vehicule");
        databaseVisits.addValueEventListener(new ValueEventListener() {
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
              String DateD=dateD.getText().toString().trim();
              String DateF= dateF.getText().toString().trim();
              String NomAg=agence.getText().toString().trim();
              String montantT=montant.getText().toString().trim();
                if (!matricule.equals("choisir un matricule") && !TextUtils.isEmpty(DateD) && !TextUtils.isEmpty(DateF) && !TextUtils.isEmpty(NomAg) && !TextUtils.isEmpty(montantT)) {

                    //getting a unique id using push().getKey() method
                    //it will create a unique id and we will use it as the Primary Key for our Artist
                    String id = nbrVisitsTechnic.push().getKey();

                    //creating an Artist Object
                    VisiteTechnic visit=new VisiteTechnic(matricule, NomAg,DateD, DateF,montantT,id,String.valueOf(numero));
                     numero++;
                    //Saving the Artist
                    nbrVisitsTechnic.child(id).setValue(visit);


                   resultat.setText("la visite technique est ajoutée avec succès");
                   resultat.setBackgroundColor(Color.GREEN);
                   dateD.setText("");
                   dateF.setText("");
                    numeroVisite.setText("Visite Technique N: "+numero);
                   agence.setText("");
                   montant.setText("");

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
