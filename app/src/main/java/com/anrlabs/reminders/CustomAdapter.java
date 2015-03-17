package com.anrlabs.reminders;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 * Created by Archie on 3/13/2015.
 */
public class CustomAdapter extends SimpleCursorAdapter {

    private Context context;
    private View row;
    private LayoutInflater layoutInflater;
    private TextView text;
    private ImageView icon;

    public CustomAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);

        this.context = context;
        this.layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row = convertView;  //getting current view

        //sets empty view
        if(row == null){
            row = this.layoutInflater.inflate(R.layout.row, null);
        }

        text = (TextView) row.findViewById(R.id.delivered); //getting deliver value
        String toDo = text.getText().toString();

        // switching color of row display by type and check if completed
        switch(toDo){
            case "1":
                text = (TextView) row.findViewById(R.id.title);
                text.setTextColor(Color.parseColor("db0518"));
                text = (TextView) row.findViewById(R.id.locationName);
                text.setTextColor(Color.parseColor("db0518"));
                icon = (ImageView) row.findViewById(R.id.icon);
                icon.setImageResource(R.drawable.om_db0518_map_marker);
                break;
            case "2":
                text = (TextView) row.findViewById(R.id.title);
                text.setTextColor(Color.parseColor("db0518"));
                text = (TextView) row.findViewById(R.id.date);
                text.setTextColor(Color.parseColor("db0518"));
                text = (TextView) row.findViewById(R.id.time);
                text.setTextColor(Color.parseColor("db0518"));
                icon = (ImageView) row.findViewById(R.id.icon);
                icon.setImageResource(R.drawable.oa_db0518_alarm_clock);
                break;
            case "3":
                text = (TextView) row.findViewById(R.id.title);
                text.setTextColor(Color.parseColor("0519db"));
                text = (TextView) row.findViewById(R.id.locationName);
                text.setTextColor(Color.parseColor("0519db"));
                icon = (ImageView) row.findViewById(R.id.icon);
                icon.setImageResource(R.drawable.dm_0519db_map_marker);
                break;
            case "4":
                text = (TextView) row.findViewById(R.id.title);
                text.setTextColor(Color.parseColor("0519db"));
                text = (TextView) row.findViewById(R.id.date);
                text.setTextColor(Color.parseColor("0519db"));
                text = (TextView) row.findViewById(R.id.time);
                text.setTextColor(Color.parseColor("0519db"));
                icon = (ImageView) row.findViewById(R.id.icon);
                icon.setImageResource(R.drawable.da_0519db_alarm_clock);
                break;
        }

        return row; //returns the update view
    }
}
