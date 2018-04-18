package br.ufma.lsdi.SDPEU.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufma.lsdi.SDPEU.R;
import br.ufma.lsdi.SDPEU.model.event.Event;

/**
 * Created by makleyston on 04/12/17.
 */

public class EventAdapter extends ArrayAdapter {
    private ArrayList<Event> arItem = new ArrayList<>();
    private Context context = null;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public EventAdapter(ArrayList<Event> arItem, Context context) {
        super(context, R.layout.fragment_event_layout, arItem);
        this.arItem = arItem;
        this.context = context;
        sharedPref = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.fragment_event_layout, parent, false);
        TextView textView = rowView.findViewById(R.id.txt_event);
        ImageView imgCheck = rowView.findViewById(R.id.imgCheck);

        textView.setText(arItem.get(position).getLabel());

        String str = sharedPref.getString(arItem.get(position).getLabel(), "");
        if(str.equals("")){
            imgCheck.setVisibility(View.GONE);
        }else{
            imgCheck.setVisibility(View.VISIBLE);
        }

        return rowView;
    }

}
