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
package de.jadehs.jadehsnavigator.task;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;

import java.util.ArrayList;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.model.InfoSys;
import de.jadehs.jadehsnavigator.model.InfoSysItem;
import de.jadehs.jadehsnavigator.response.InfoSysAsyncResponse;

public class ParseInfoSysTask extends AsyncTask<Void, Void, ArrayList<InfoSysItem>> {
    public InfoSysAsyncResponse delegate=null;

    private Activity activity = null;
    private String url = null;
    private int fb;
    private InfoSys infoSys = null;

    private boolean isSwiperefresh = false;

    /**
     * constructs a new task
     *
     * @param activity parent activity
     * @param url the url to parse
     * @param fb the fb this infosys belongs to
     * @param isSwiperefresh
     */
    public ParseInfoSysTask(Activity activity, String url, int fb, boolean isSwiperefresh){
        this.activity = activity;
        this.url = url;
        this.fb = fb;
        this.isSwiperefresh = isSwiperefresh;
    }

    @Override
    protected ArrayList<InfoSysItem> doInBackground(Void... params) {
        // InfoSys mit der gegebenen URL parsen
        this.infoSys = new InfoSys(this.activity.getApplicationContext(), url, fb);
        ArrayList<InfoSysItem> infoSysItems = this.infoSys.parse();

        return infoSysItems;
    }

    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
        if(!this.isSwiperefresh) {
            try {
                activity.findViewById(R.id.progressInfoSys).setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(ArrayList<InfoSysItem> items) {
        // fire 'processFinish' method in InfoSysFragment
        delegate.processFinish(items);
    }

    public InfoSys getInfoSys() {
        return infoSys;
    }

    public void setInfoSys(InfoSys infoSys) {
        this.infoSys = infoSys;
    }
}