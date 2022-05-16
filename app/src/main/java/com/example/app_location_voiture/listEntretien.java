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

public class listEntretien extends AppCompatActivity {
    ListView listViewEntretens;
    DatabaseReference databaseE;

    List<Entretien> entretiens;
    EditText rechercher;
    ImageView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_entretien);
        entretiens=new ArrayList<>();
        listViewEntretens=(ListView) findViewById(R.id.listViewEntre);
        databaseE= FirebaseDatabase.getInstance().getReference("Entretien");
        rechercher=(EditText) findViewById(R.id.chercherN);
        search=(ImageView) findViewById(R.id.searchO);
        search.setClickable(true);
        String searchValue=rechercher.getText().toString().trim();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(listEntretien.this, "rechercher", Toast.LENGTH_SHORT).show();
                Query firebaseSearch=databaseE.orderByChild("numero").equalTo(rechercher.getText().toString().trim());
                firebaseSearch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //clearing the previous artist list
                        entretiens.clear();

                        //iterating through all the nodes
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            Entretien entre = postSnapshot.getValue(Entretien.class);
                            //adding artist to the list
                            entretiens.add(entre);

                        }

                        //creating adapter
                        EntreAdapter entreAdapter = new EntreAdapter(listEntretien.this, entretiens);
                        //attaching adapter to the listview
                        listViewEntretens.setAdapter(entreAdapter);
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
                entretiens.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Entretien entre = postSnapshot.getValue(Entretien.class);
                    //adding artist to the list
                    entretiens.add(entre);

                }

                //creating adapter
                EntreAdapter entreAdapter = new EntreAdapter(listEntretien.this, entretiens);
                //attaching adapter to the listview
                listViewEntretens.setAdapter(entreAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewEntretens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Entretien entretien = entretiens.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), AfficherEntre.class);

                //putting artist name and id to intent
                intent.putExtra("matricule", entretien.getMatricule());
                intent.putExtra("numeroEntretien", entretien.getNumero());
                intent.putExtra("DateEntretien", entretien.getDateEntretien());
                intent.putExtra("type", entretien.getTypeEntretien());
                intent.putExtra("montant", entretien.getMontant());
                intent.putExtra("idEntretien",entretien.getIdEntretien());
                //starting the activity with intent
                startActivity(intent);
            }
        });

    }
}