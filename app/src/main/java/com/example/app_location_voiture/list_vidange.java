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

public class list_vidange extends AppCompatActivity {
    ListView listViewVidange;
    DatabaseReference databasev;

    List<Vidange> vidanges;
    EditText rechercher;
    ImageView search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_vidange);
        vidanges=new ArrayList<>();
        listViewVidange=(ListView) findViewById(R.id.listViewV);
        databasev= FirebaseDatabase.getInstance().getReference("Vidange");
        rechercher=(EditText) findViewById(R.id.chercherV);
        search=(ImageView) findViewById(R.id.searchVO);
        search.setClickable(true);
        String searchValue=rechercher.getText().toString().trim();
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Query firebaseSearch;
                firebaseSearch = databasev.orderByChild("numeroV").equalTo(rechercher.getText().toString().trim());
                firebaseSearch.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //clearing the previous artist list
                        vidanges.clear();

                        //iterating through all the nodes
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            //getting artist
                            Vidange entre = postSnapshot.getValue(Vidange.class);
                            //adding artist to the list
                            vidanges.add(entre);

                        }

                        //creating adapter
                        VidangeAdapter vAdapter = new VidangeAdapter(list_vidange.this, vidanges);
                        //attaching adapter to the listview
                        listViewVidange.setAdapter(vAdapter);
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
                vidanges.clear();

                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Vidange entre = postSnapshot.getValue(Vidange.class);
                    //adding artist to the list
                    vidanges.add(entre);

                }

                //creating adapter
                VidangeAdapter vidangeAdapter = new VidangeAdapter(list_vidange.this, vidanges);
                //attaching adapter to the listview
                listViewVidange.setAdapter(vidangeAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listViewVidange.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //getting the selected artist
                Vidange vidange1= vidanges.get(i);

                //creating an intent
                Intent intent = new Intent(getApplicationContext(), AfficherVidanges.class);

                //putting artist name and id to intent
                intent.putExtra("matricule", vidange1.getMatricule());
                intent.putExtra("numeroVidange",vidange1.getNumeroV());
                intent.putExtra("DateVidange", vidange1.getDateV());
                intent.putExtra("typev", vidange1.getTypeV());
                intent.putExtra("montant", vidange1.getMontant());
                intent.putExtra("idVidange",vidange1.getIdVidange());
                intent.putExtra("keloP",vidange1.getKmP());
                //starting the activity with intent
                startActivity(intent);
            }
        });
    }
}