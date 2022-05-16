package com.example.app_location_voiture;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class list_Vehicule extends AppCompatActivity {
    ListView listViewVehicule;
    DatabaseReference databasev;

    List<Vehicule> vehicules;
    EditText rechercher;
    ImageView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vehicule);
        vehicules=new ArrayList<>();
        listViewVehicule=(ListView) findViewById(R.id.listViewVehicule);
        databasev= FirebaseDatabase.getInstance().getReference("Vehicule");
        rechercher=(EditText) findViewById(R.id.chercherV);
        search=(ImageView) findViewById(R.id.searchO);
        search.setClickable(true);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query firebaseSearch;
                firebaseSearch = databasev.orderByChild("mark").startAt(rechercher.getText().toString().trim()).endAt(rechercher.getText().toString().trim()+"\uf8ff");
                firebaseSearch.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //clearing the previous artist list
                        vehicules.clear();

                        //iterating through all the nodes
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            Vehicule entre = postSnapshot.getValue(Vehicule.class);
                            //adding artist to the list
                            vehicules.add(entre);

                        }

                        //creating adapter
                        VehiculeAdapter vAdapter = new VehiculeAdapter(list_Vehicule.this, vehicules);
                        //attaching adapter to the listview
                        listViewVehicule.setAdapter(vAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
        databasev.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //clearing the previous artist list
                vehicules.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Vehicule v = postSnapshot.getValue(Vehicule.class);
                    //adding artist to the list
                    vehicules.add(v);

                }

                //creating adapter
                VehiculeAdapter vidangeAdapter = new VehiculeAdapter(list_Vehicule.this, vehicules);
                //attaching adapter to the listview
                listViewVehicule.setAdapter(vidangeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewVehicule.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Vehicule vehicule= vehicules.get(i);
                StorageReference databaseStorage= FirebaseStorage.getInstance().getReference("imagesVehicule").child(vehicule.getIdVehicule()+"."+vehicule.getExtensionImage());
                //creating an intent
                Intent intent = new Intent(getApplicationContext(), AfficherVehicules.class);

                //putting artist name and id to intent
                intent.putExtra("matricule", vehicule.getMatricule());
                intent.putExtra("mark",vehicule.getMark());
                intent.putExtra("model", vehicule.getModel());
                intent.putExtra("puissance", vehicule.getPuissance());
                intent.putExtra("etat", vehicule.getEtat());
                intent.putExtra("kmA",vehicule.getKm());
                intent.putExtra("PrixJ",vehicule.getPrixJ());
                intent.putExtra("PrixM",vehicule.getPrixM());
                intent.putExtra("idVehicule",vehicule.getIdVehicule());
                intent.putExtra("extension",vehicule.getExtensionImage());

                //starting the activity with intent
                startActivity(intent);
            }
        });
    }
}