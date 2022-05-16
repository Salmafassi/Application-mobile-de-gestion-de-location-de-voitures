package com.example.app_location_voiture;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EntreAdapter extends ArrayAdapter {
    private Activity context;
    List<Entretien> entretiens;
    public  EntreAdapter(Activity context, List<Entretien> entretienes) {
        super(context, R.layout.list_layout, entretienes);
        this.context = context;
        this.entretiens = entretienes;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);
        TextView textViewNumber = (TextView) listViewItem.findViewById(R.id.entreNum);
        Entretien entre= entretiens.get(position);
        textViewNumber.setText("Entretien N: "+entre.getNumero());
        return listViewItem;
    }
}
