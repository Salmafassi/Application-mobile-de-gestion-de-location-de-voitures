package com.example.app_location_voiture;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VisitAdapter extends ArrayAdapter {
    private Activity context;
    List<VisiteTechnic> Visits;
    public  VisitAdapter(Activity context, List<VisiteTechnic> visits) {
        super(context, R.layout.list_layout_visite, visits);
        this.context = context;
        this.Visits = visits;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_visite, null, true);
        TextView textViewNumber = (TextView) listViewItem.findViewById(R.id.VisitNum);
        VisiteTechnic visitTechnic= Visits.get(position);
        textViewNumber.setText("Visite Technique N: "+visitTechnic.getNumeroVisite());
        return listViewItem;
    }
}
