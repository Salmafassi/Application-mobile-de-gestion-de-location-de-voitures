package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AfficherVisits extends AppCompatActivity {
    TextView matricule;
    TextView dateD;
    TextView dateF;
    TextView montant;
    TextView resultat;
    TextView numero;
    String idVisits;
    TextView nomAgence;
    Button update;
    Button delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_visits);
        matricule=(TextView) findViewById(R.id.matriculeValue1);
        dateD=(TextView) findViewById(R.id.dateValueD);
        dateF=(TextView) findViewById(R.id.dateFinValue);
        montant=(TextView)findViewById(R.id.montantValue);
        resultat=(TextView) findViewById(R.id.resultat1);
        numero=(TextView)findViewById(R.id.numeroVisits);
        nomAgence=(TextView)findViewById(R.id.nomAgValue);
        update=(Button)findViewById(R.id.update1);
        delete=(Button)findViewById(R.id.delete1);
        Intent intent = getIntent();
        idVisits=intent.getStringExtra("idVisit");
        matricule.setText(intent.getStringExtra("matricule"));
        dateD.setText(intent.getStringExtra("DateDebut"));
        dateF.setText(intent.getStringExtra("DateFin"));
        montant.setText(intent.getStringExtra("montant"));
        nomAgence.setText(intent.getStringExtra("NomAgence"));
        numero.setText("la visite technique N: "+intent.getStringExtra("numeroVisite"));
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDeleteDialog(intent,numero.getText().toString());
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d=new AlertDialog.Builder(AfficherVisits.this)
                        .setTitle("Voulez-vous vraiment supprimer la visite technique N: "+intent.getStringExtra("numeroVisite")+" ?")
                        .setNegativeButton("Annuler",null)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("VisitesTechnics").child(idVisits);
                                dR.removeValue();
                                Intent intent = new Intent(getApplicationContext(), List_VisiteTechnique.class);
                                startActivity(intent);
                            }
                        })
                        .create();
                d.setOwnerActivity(AfficherVisits.this);
                d.show();



            }
        });

    }
    private void showUpdateDeleteDialog(Intent intent, String visitTechnicNum) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_visits, null);
        dialogBuilder.setView(dialogView);
        DatabaseReference databaseVehicule = FirebaseDatabase.getInstance().getReference("Vehicule");
        List<Vehicule> vehicules = new ArrayList<>();

        ArrayAdapter<String> adapter;
        ArrayList<String> newone = new ArrayList<>();
        EditText nomAG=(EditText) dialogView.findViewById(R.id.agence);
        EditText montantd = (EditText) dialogView.findViewById(R.id.montant);

        TextView Datedebut = (TextView) dialogView.findViewById(R.id.dateDebut);
        TextView Datefin=(TextView) dialogView.findViewById(R.id.dateFin);
        Spinner sp = (Spinner) dialogView.findViewById(R.id.spinner);
        TextView resultat2 = (TextView) dialogView.findViewById(R.id.resultatVisit);
        Button annuler=(Button)dialogView.findViewById(R.id.annuler1);
        String numberV= intent.getStringExtra("numeroVisite");
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        Button save = (Button) dialogView.findViewById(R.id.save1);
        Datedebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AfficherVisits.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Datedebut.setText(year + "/" + month + "/" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.setTitle("Choisir une date :");
                datePickerDialog.show();
            }
        });
        Datefin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AfficherVisits.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Datefin.setText(year + "/" + month + "/" + dayOfMonth);
                    }
                }, year, month, day);
                datePickerDialog.setTitle("Choisir une date :");
                datePickerDialog.show();
            }
        });
        newone.add("choisir un matricule");
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
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, newone);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        dialogBuilder.setTitle(visitTechnicNum);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matricule1 = sp.getSelectedItem().toString();
                String Datedebut1 = Datedebut.getText().toString().trim();
                String DateFin1=Datefin.getText().toString().trim();
                String nomAgence1=nomAG.getText().toString().trim();

                String montantT = montantd.getText().toString().trim();
                if (!matricule1.equals("choisir un matricule") && !TextUtils.isEmpty(Datedebut1) && !TextUtils.isEmpty(DateFin1) && !TextUtils.isEmpty(nomAgence1) && !TextUtils.isEmpty(montantT) ) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("VisitesTechnics").child(idVisits);
                    VisiteTechnic visit = new VisiteTechnic(matricule1,nomAgence1,Datedebut1, DateFin1, montantT,idVisits, numberV);

                    dR.setValue(visit);

                    b.dismiss();
                    resultat.setText("la viste technique est modifiée avec succès");
                    resultat.setBackgroundColor(Color.GREEN);
                    matricule.setText(matricule1);
                    dateD.setText(Datedebut1);
                    dateF.setText(DateFin1);
                    nomAgence.setText(nomAgence1);

                    montant.setText(montantT);

                } else {
                    //if the value is not given displaying a toast
                    resultat2.setText("Tous les champs sont requis");
                    resultat2.setBackgroundColor(Color.RED);
                }
            }
        });
        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b.dismiss();
                resultat.setText("l'operation est annulée");
                resultat.setBackgroundColor(Color.rgb(250, 200, 152));
            }
        });
    }
    }
