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

public class AfficherVidanges extends AppCompatActivity {
    TextView matricule;
    TextView date;
    TextView type;
    TextView montant;
    TextView resultat;
    TextView numero;
    String idVidange;
    TextView kmp;
    Button update;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_vidanges);
        matricule = (TextView) findViewById(R.id.matriculeValue);
        date = (TextView) findViewById(R.id.dateVidange);
        type = (TextView) findViewById(R.id.typeVidange);
        montant = (TextView) findViewById(R.id.montantVidange);
        numero = (TextView) findViewById(R.id.numeroVidange);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        resultat = (TextView) findViewById(R.id.resultat);
        kmp = (TextView) findViewById(R.id.kmp);
        Intent intent = getIntent();
        idVidange = intent.getStringExtra("idVidange");
        matricule.setText(intent.getStringExtra("matricule"));
        numero.setText("Vidange N: " + intent.getStringExtra("numeroVidange"));
        date.setText(intent.getStringExtra("DateVidange"));
        type.setText(intent.getStringExtra("typev"));
        montant.setText(intent.getStringExtra("montant"));
        kmp.setText(intent.getStringExtra("keloP"));
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDeleteDialog(intent, numero.getText().toString());
            }


        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new AlertDialog.Builder(AfficherVidanges.this)
                        .setTitle("Voulez-vous vraiment supprimer le vidange N: " + intent.getStringExtra("numeroVidange") + " ?")
                        .setNegativeButton("Annuler", null)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Vidange").child(idVidange);
                                dR.removeValue();
                                Intent intent = new Intent(getApplicationContext(), listEntretien.class);
                                startActivity(intent);
                            }
                        })
                        .create();
                d.setOwnerActivity(AfficherVidanges.this);
                d.show();


            }
        });

    }

    private void showUpdateDeleteDialog(Intent intent, String entretienNum) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_vidange, null);
        dialogBuilder.setView(dialogView);
        DatabaseReference databaseVehicule = FirebaseDatabase.getInstance().getReference("Vehicule");
        List<Vehicule> vehicules = new ArrayList<>();

        ArrayAdapter<String> adapter;
        ArrayList<String> newone = new ArrayList<>();
        TextView kmp1 = (TextView) dialogView.findViewById(R.id.kmPV);
        EditText montant1 = (EditText) dialogView.findViewById(R.id.montantV);
        TextView kma = (TextView) dialogView.findViewById(R.id.kmaV);
        TextView DateV = (TextView) dialogView.findViewById(R.id.dateVidang);
        Spinner typeV = (Spinner) dialogView.findViewById(R.id.spinnerV1);
        Spinner sp = (Spinner) dialogView.findViewById(R.id.spinnerV);
        TextView resultat1 = (TextView) dialogView.findViewById(R.id.resultatv);
        Button annuler = (Button) dialogView.findViewById(R.id.annuler1);
        String numberV = intent.getStringExtra("numeroVidange");
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        Button save = (Button) dialogView.findViewById(R.id.save1);
        dialogBuilder.setTitle(entretienNum);
        final AlertDialog b = dialogBuilder.create();
        b.show();
        DateV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AfficherVidanges.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateV.setText(year + "/" + month + "/" + dayOfMonth);
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
                            if (vehicule.getMatricule().equals(newone.get(position))) {
                                kma.setText(vehicule.getKm());
                                int kmActual = Integer.parseInt(kma.getText().toString());
                                int valeur = Integer.parseInt(typeV.getSelectedItem().toString());
                                int result = kmActual + valeur;
                                kmp1.setText(String.valueOf(result));

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
        typeV.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int kmActual = Integer.parseInt(kma.getText().toString());
                int valeur = Integer.parseInt(typeV.getSelectedItem().toString());
                int result = kmActual + valeur;
                kmp1.setText(String.valueOf(result));

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matricule1 = sp.getSelectedItem().toString();
                String DateVd = DateV.getText().toString().trim();
                String typevid = typeV.getSelectedItem().toString();
                String kmActual = kma.getText().toString().trim();
                String kmProchain = kmp1.getText().toString().trim();
                String montantT = montant1.getText().toString().trim();
                if (!matricule1.equals("choisir un matricule") && !TextUtils.isEmpty(DateVd) && !TextUtils.isEmpty(typevid) && !TextUtils.isEmpty(montantT) && !TextUtils.isEmpty(kmActual) && !TextUtils.isEmpty(kmProchain)) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Vidange").child(idVidange);
                    Vidange vidang = new Vidange(String.valueOf(numberV), matricule1, DateVd, montantT, typevid, kmProchain, idVidange);

                    dR.setValue(vidang);

                    b.dismiss();
                    resultat.setText("le vidange est modifié avec succès");
                    resultat.setBackgroundColor(Color.GREEN);
                    matricule.setText(matricule1);
                    date.setText(DateVd);
                    type.setText(typevid);

                    kmp.setText(kmProchain);
                    montant.setText(montantT);

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
                resultat.setText("l'operation est annulée");
                resultat.setBackgroundColor(Color.rgb(250, 200, 152));
            }
        });

    }
}