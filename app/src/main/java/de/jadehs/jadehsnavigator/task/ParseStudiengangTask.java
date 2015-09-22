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

import java.util.ArrayList;

import de.jadehs.jadehsnavigator.model.Studiengang;
import de.jadehs.jadehsnavigator.model.StudiengangItem;
import de.jadehs.jadehsnavigator.response.StudiengangAsyncResponse;

public class ParseStudiengangTask extends AsyncTask<Void, Void, ArrayList<StudiengangItem>> {

    public StudiengangAsyncResponse delegate = null;

    private Activity activity = null;
    private int fb;
    private Studiengang studiengang = null;

    public ParseStudiengangTask(Activity activity, int fb) {
        this.activity = activity;
        this.fb = fb;
    }

    @Override
    protected ArrayList<StudiengangItem> doInBackground(Void... params) {

        this.studiengang = new Studiengang(this.activity.getApplicationContext(), fb);
        ArrayList<StudiengangItem> studiengangItems = this.studiengang.parseStudiengang();
        return studiengangItems;
    }

    @Override
    protected void onPostExecute(ArrayList<StudiengangItem> studiengangItems) {
        delegate.processFinishedStudiengang(studiengangItems);
    }

}
