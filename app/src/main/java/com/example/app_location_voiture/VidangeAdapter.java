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

public class VidangeAdapter extends ArrayAdapter {
    private Activity context;
    List<Vidange> vidanges;
    public  VidangeAdapter(Activity context, List<Vidange> vidange1) {
        super(context, R.layout.list_layout_vidange, vidange1);
        this.context = context;
        this.vidanges = vidange1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout_vidange, null, true);
        TextView textViewNumber = (TextView) listViewItem.findViewById(R.id.VidangeNum);
        Vidange vidang= vidanges.get(position);
        textViewNumber.setText("Vidange N: "+vidang.getNumeroV());
        return listViewItem;
    }
}
