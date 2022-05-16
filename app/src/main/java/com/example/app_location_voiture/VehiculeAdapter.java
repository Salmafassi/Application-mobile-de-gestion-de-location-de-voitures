package com.example.app_location_voiture;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VehiculeAdapter extends ArrayAdapter {
    private Activity context;
    List<Vehicule> vehicule;

    public  VehiculeAdapter(Activity context, List<Vehicule> vehicules) {
        super(context, R.layout.list_layout, vehicules);
        this.context = context;
        this.vehicule = vehicules;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.list_layout_vehicule, null, true);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.VehiculeM);
        TextView textViewpJ=(TextView)listViewItem.findViewById(R.id.PrixJ);
        TextView textViewM=(TextView)listViewItem.findViewById(R.id.PrixM);

        ImageView imageV=(ImageView)listViewItem.findViewById(R.id.image);
       Vehicule vehiInstance=vehicule.get(position);

        StorageReference databaseStorage= FirebaseStorage.getInstance().getReference("imagesVehicule").child(vehiInstance.getIdVehicule()+"."+vehiInstance.getExtensionImage());
        textViewName.setText(vehiInstance.getMark()+" ("+vehiInstance.getModel()+")");
        textViewpJ.setText(vehiInstance.getPrixJ());
        textViewM.setText(vehiInstance.getPrixM());
        databaseStorage.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downUri = task.getResult();
                    String imageUrl = downUri.toString();
                    Picasso.get().load(imageUrl).resize(360,200).into(imageV);

                }else{

                }
            }
        });
        return listViewItem;
    }
}
