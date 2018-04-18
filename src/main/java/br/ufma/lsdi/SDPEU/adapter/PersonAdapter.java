package br.ufma.lsdi.SDPEU.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufma.lsdi.SDPEU.R;

/**
 * Created by makleyston on 04/12/17.
 */

public class PersonAdapter extends ArrayAdapter {
    private ArrayList<String> arItem = new ArrayList<>();
    private Context context = null;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    public PersonAdapter(ArrayList<String> arItem, Context context) {
        super(context, R.layout.fragment_person_layout, arItem);
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

        View rowView = inflater.inflate(R.layout.fragment_person_layout, parent, false);
        TextView textView = rowView.findViewById(R.id.txt_title);
        final EditText editText = rowView.findViewById(R.id.edt_field);

        textView.setText(formatTitle(arItem.get(position)));

        String str = sharedPref.getString(arItem.get(position), "");
        editText.setText(str);

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    editor.putString(arItem.get(position), editText.getText().toString());
                    editor.commit();
                }
            }
        });

        return rowView;
    }

    private String formatTitle(String title){
        return title.substring(0, 1).toUpperCase() + title.substring(1);
    }

}
