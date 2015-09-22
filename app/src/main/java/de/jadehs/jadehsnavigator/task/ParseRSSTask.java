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
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.model.RSS;
import de.jadehs.jadehsnavigator.model.RSSItem;
import de.jadehs.jadehsnavigator.model.RSSOrigin;
import de.jadehs.jadehsnavigator.response.RSSAsyncResponse;

public class ParseRSSTask extends AsyncTask<Void, Void, ArrayList<RSSItem>> {
    public RSSAsyncResponse delegate=null;

    private Activity activity = null;
    private ArrayList<RSSOrigin> origins;

    private RSS rss;

    /**
     *
     * @param activity parent activity
     * @param origins A RSSOrigin object to parse
     */
    public ParseRSSTask(Activity activity, ArrayList<RSSOrigin> origins){
        this.activity = activity;
        this.origins = origins;
    }

    @Override
    protected ArrayList<RSSItem> doInBackground(Void... params) {
        this.rss = new RSS(this.activity.getApplicationContext(), origins);
        boolean result = this.rss.readAll();
        int sz = this.rss.getRssItems().size();
        Log.wtf("RSS", "FINISHED PARSING ORIGIN WITH " + result + " AND " + sz + " entries");

        return this.rss.getRssItems();
    }

    @Override
    protected void onPreExecute() {
        try {
            activity.findViewById(R.id.progressNews).setVisibility(View.VISIBLE);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(ArrayList<RSSItem> items) {
        // fire the method 'processFinish' in NewsFragment
        delegate.processFinish(items);
    }
}
