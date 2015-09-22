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
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.model.InfoSysItem;
import de.jadehs.jadehsnavigator.view.ExpandableTextView;

public class InfoSysItemAdapter extends BaseAdapter{
    private static final String TAG = "INFOSYSITEMADAPTER";

    private Context context;
    private ArrayList<InfoSysItem> infoSysItems;

    public InfoSysItemAdapter(Context context, ArrayList<InfoSysItem> infoSysItems) {
        this.context = context;
        this.infoSysItems = infoSysItems;
    }

    @Override
    public int getCount() {
        return this.infoSysItems.size();
    }

    @Override
    public Object getItem(int position) {
        return this.infoSysItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    this.context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.infosys_list_item, null);
        }

        /* Set all texts for a single item */
        TextView txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        ExpandableTextView txtDescription = (ExpandableTextView) convertView.findViewById(R.id.txtDescription);
        TextView txtFooter = (TextView) convertView.findViewById(R.id.txtFooter);

        txtTitle.setText(this.infoSysItems.get(position).getTitle());
        txtDescription.setMovementMethod(LinkMovementMethod.getInstance()); // Automatically convert links to be clickable
        txtDescription.setText(Html.fromHtml(this.infoSysItems.get(position).getDescription()));
        txtFooter.setText(this.infoSysItems.get(position).getCreator() + ": " + this.infoSysItems.get(position).getCreated());

        return convertView; // Returns the created View
    }
}