/**
 * This file is part of JadeHS-Navigator.
 *
 * JadeHS-Navigator is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * JadeHS-Navigator is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with JadeHS-Navigator.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.jadehs.jadehsnavigator.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.StudiengangActivity;
import de.jadehs.jadehsnavigator.model.StudiengangItem;

/**
 * Created by Nico on 11.08.2015.
 */
public class StudiengangAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<StudiengangItem> studiengangItems;

    private String studiengang;

    public StudiengangAdapter (Context context, ArrayList<StudiengangItem> studiengangItems) {
        this.context = context;
        this.studiengangItems = studiengangItems;
    }

    @Override
    public int getCount() {
        return this.studiengangItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.studiengangItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.studiengang_list_item, null);
        }

        final RadioButton btnStudiengang = (RadioButton) convertView.findViewById(R.id.btnStudiengang);
        btnStudiengang.setText(this.studiengangItems.get(position).getStudiengangName());
        btnStudiengang.setChecked(false);

        final String studiegangID = this.studiengangItems.get(position).getStudiengangID();

        btnStudiengang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                studiengang = btnStudiengang.getText().toString();

                SharedPreferences sp = context.getSharedPreferences("JHSNAV_PREFS", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("StudiengangID", studiegangID);
                // add studiengang string
                editor.putString("StudiengangName", studiengang);
                editor.commit();

                Toast.makeText(context, studiengang + " ausgewählt", Toast.LENGTH_LONG).show();
                try {
                    ((StudiengangActivity) context).finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return convertView;
    }

    public String getStudiengang() {
        return studiengang;
    }

    public void setStudiengang(String studiengang) {
        this.studiengang = studiengang;
    }


}
