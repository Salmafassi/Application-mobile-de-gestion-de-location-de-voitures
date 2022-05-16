package com.example.app_location_voiture;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class List_VisiteTechnique extends AppCompatActivity {
    ListView listViewVisit;
    DatabaseReference databaseE;

    List<VisiteTechnic> visitTechnic;
    EditText rechercher;
    ImageView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_visite_technique);
        visitTechnic=new ArrayList<>();
        listViewVisit=(ListView) findViewById(R.id.listViewEntre);
        databaseE= FirebaseDatabase.getInstance().getReference("VisitesTechnics");
        rechercher=(EditText) findViewById(R.id.chercherN);
        search=(ImageView) findViewById(R.id.searchO);
        search.setClickable(true);
        String searchValue=rechercher.getText().toString().trim();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query firebaseSearch=databaseE.orderByChild("numeroVisite").equalTo(rechercher.getText().toString().trim());
                firebaseSearch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //clearing the previous artist list
                        visitTechnic.clear();

                        //iterating through all the nodes
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            VisiteTechnic visitTechnic1 = postSnapshot.getValue(VisiteTechnic.class);
                            //adding artist to the list
                            visitTechnic.add(visitTechnic1);

                        }

                        //creating adapter
                        VisitAdapter visitAdapter = new VisitAdapter(List_VisiteTechnique.this, visitTechnic);
                        //attaching adapter to the listview
                        listViewVisit.setAdapter(visitAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        databaseE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                visitTechnic.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    VisiteTechnic entre = postSnapshot.getValue(VisiteTechnic.class);
                    //adding artist to the list
                    visitTechnic.add(entre);

                }

                //creating adapter
                VisitAdapter visitAdapter = new VisitAdapter(List_VisiteTechnique.this, visitTechnic);
                //attaching adapter to the listview
                listViewVisit.setAdapter(visitAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewVisit.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                VisiteTechnic visits = visitTechnic.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), AfficherVisits.class);

                //putting artist name and id to intent
                intent.putExtra("matricule", visits.getMatricule());
                intent.putExtra("numeroVisite",visits.numeroVisite);
                intent.putExtra("DateDebut", visits.dateDebut);
                intent.putExtra("DateFin", visits.dateFin);
                intent.putExtra("montant", visits.getMontant());
                intent.putExtra("NomAgence",visits.getNom_agence());
                intent.putExtra("idVisit",visits.getIdVisite());
                //starting the activity with intent
                startActivity(intent);
            }
        });
    }
}