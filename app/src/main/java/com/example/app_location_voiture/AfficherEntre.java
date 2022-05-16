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

public class AfficherEntre extends AppCompatActivity {
    TextView matricule;
    TextView date;
    TextView type;
    TextView montant;
    TextView resultat;
    TextView numero;
    String idEntretien;
    Button update;
    Button delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afficher_entre);
        matricule = (TextView) findViewById(R.id.matriculeValue);
        date = (TextView) findViewById(R.id.dateValue);
        type = (TextView) findViewById(R.id.typeValue);
        montant = (TextView) findViewById(R.id.montantValue);
        numero = (TextView) findViewById(R.id.numeroEntretien);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        resultat=(TextView)findViewById(R.id.resultat) ;
        Intent intent = getIntent();
        idEntretien = intent.getStringExtra("idEntretien");
        matricule.setText(intent.getStringExtra("matricule"));
        numero.setText("Entretien N: " + intent.getStringExtra("numeroEntretien"));
        date.setText(intent.getStringExtra("DateEntretien"));
        type.setText(intent.getStringExtra("type"));
        montant.setText(intent.getStringExtra("montant"));
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showUpdateDeleteDialog(intent,numero.getText().toString());
            }
        });
       delete.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Dialog d=new AlertDialog.Builder(AfficherEntre.this)
                       .setTitle("Voulez-vous vraiment supprimer l'entretien N: "+intent.getStringExtra("numeroEntretien")+" ?")
                       .setNegativeButton("Annuler",null)
                       .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Entretien").child(idEntretien);
                               dR.removeValue();
                               Intent intent = new Intent(getApplicationContext(), listEntretien.class);
                               startActivity(intent);
                           }
                       })
                       .create();
               d.setOwnerActivity(AfficherEntre.this);
               d.show();



           }
       });

    }

    private void showUpdateDeleteDialog(Intent intent, String entretienNum) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog, null);
        dialogBuilder.setView(dialogView);
        DatabaseReference databaseVehicule = FirebaseDatabase.getInstance().getReference("Vehicule");
        List<Vehicule> vehicules = new ArrayList<>();

        ArrayAdapter<String> adapter;
        ArrayList<String> newone = new ArrayList<>();
        EditText type1 = (EditText) dialogView.findViewById(R.id.type);
        EditText montant1 = (EditText) dialogView.findViewById(R.id.montant);

        TextView DateE = (TextView) dialogView.findViewById(R.id.dateEntretien);

        Spinner sp = (Spinner) dialogView.findViewById(R.id.spinner);
        TextView resultat1 = (TextView) dialogView.findViewById(R.id.resultat);
        Button annuler=(Button)dialogView.findViewById(R.id.annuler);
        String numberE= intent.getStringExtra("numeroEntretien");
        final Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        final int month = c.get(Calendar.MONTH);
        final int day = c.get(Calendar.DAY_OF_MONTH);
        Button save = (Button) dialogView.findViewById(R.id.save);
        DateE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AfficherEntre.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateE.setText(year + "/" + month + "/" + dayOfMonth);
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

        dialogBuilder.setTitle(entretienNum);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String matricule1 = sp.getSelectedItem().toString();
                String DateEntr = DateE.getText().toString().trim();
                String typeE = type1.getText().toString().trim();

                String montantT = montant1.getText().toString().trim();
                if (!matricule1.equals("choisir un matricule") && !TextUtils.isEmpty(DateEntr) && !TextUtils.isEmpty(typeE) && !TextUtils.isEmpty(montantT)) {
                    DatabaseReference dR = FirebaseDatabase.getInstance().getReference("Entretien").child(idEntretien);
                    Entretien entretien = new Entretien(String.valueOf(numberE), DateEntr, matricule1, typeE, montantT);
                    entretien.setIdEntretien(idEntretien);
                    dR.setValue(entretien);

                    b.dismiss();
                    resultat.setText("l'entretien est modifié avec succès");
                    resultat.setBackgroundColor(Color.GREEN);
                    matricule.setText(matricule1);
                    date.setText(DateEntr);
                    type.setText(typeE);
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

