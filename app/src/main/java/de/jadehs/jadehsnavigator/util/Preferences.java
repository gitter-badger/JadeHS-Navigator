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
package de.jadehs.jadehsnavigator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.HashSet;
import java.util.Set;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.task.ParseStudiengangTask;

public class Preferences {
    private final Context context;
    private final SharedPreferences sharedPrefs;

    public static final String PREFS_NAME = "JHSNAV_PREFS";

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    private int fb;
    private String infoSysURL;
    private String vplanURL;
    private String location; // int macht vermutlich mehr Sinn, we'll see

    public Preferences(Context context){
        this.context = context;
        this.sharedPrefs = this.context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        initPreferences();
    }

    /**
     * Initializes the preferences. Sets URL for InfoSys, VPlan and the location
     */
    public void initPreferences(){
        try {
            this.fb = Integer.parseInt(this.sharedPrefs.getString("FBPreference_list", "1")); // sharedPrefs.getInt funktioniert nicht..
        }catch (Exception ex){
            //ex.printStackTrace();
            Log.wtf("PREFERENCE_ERROR", "Got an Error!", ex);
        }

        switch (this.fb){
            case 1:
                // MIT
                this.infoSysURL = this.context.getString(R.string.infosys_base_url) + this.context.getString(R.string.infosys_url_MIT);
                this.vplanURL = context.getString(R.string.strVPlanBaseURL) + context.getString(R.string.infosys_url_MIT) + "&identifier=";

                this.location = context.getResources().getString(R.string.bez_WHV);

                break;
            case 2:
                // I
                this.infoSysURL = this.context.getString(R.string.infosys_base_url) + this.context.getString(R.string.infosys_url_I);
                this.vplanURL = context.getString(R.string.strVPlanBaseURL) + context.getString(R.string.infosys_url_I) + "&identifier=";
                this.location = context.getResources().getString(R.string.bez_WHV);

                break;
            case 3:
                // W
                this.infoSysURL = this.context.getString(R.string.infosys_base_url) + this.context.getString(R.string.infosys_url_W);
                this.vplanURL = context.getString(R.string.strVPlanBaseURL) + context.getString(R.string.infosys_url_W) + "&identifier=";
                this.location = context.getResources().getString(R.string.bez_WHV);

                break;
            case 4:
                // A
                this.infoSysURL = this.context.getString(R.string.infosys_base_url) + this.context.getString(R.string.infosys_url_A);
                this.vplanURL = context.getString(R.string.strVPlanBaseURL) + context.getString(R.string.infosys_url_A) + "&identifier=";
                this.location = context.getResources().getString(R.string.bez_OLB);

                break;
            case 5:
                // BUG
                this.infoSysURL = this.context.getString(R.string.infosys_base_url) + this.context.getString(R.string.infosys_url_BUA);
                this.vplanURL = context.getString(R.string.strVPlanBaseURL) + context.getString(R.string.infosys_url_BUA) + "&identifier=";
                this.location = context.getResources().getString(R.string.bez_OLB);

                break;
            case 6:
                // S
                this.infoSysURL = this.context.getString(R.string.infosys_base_url) + this.context.getString(R.string.infosys_url_S);
                this.vplanURL = context.getString(R.string.strVPlanBaseURL) + context.getString(R.string.infosys_url_S) + "&identifier=";
                this.location = context.getResources().getString(R.string.bez_ELS);

                break;

            default:
                Toast.makeText(this.context, "Fachbereich wurde nicht gefunden", Toast.LENGTH_LONG).show();
                break;
        }
    }


    /**
     * saves a value to this key
     *
     * @param key the key to save the preference as
     * @param value the value to save (String)
     */
    public void save(String key, String value){
        Editor editor;

        editor = this.sharedPrefs.edit();

        editor.putString(key, value);

        editor.commit();
    }

    /**
     * saves a value to this key
     *
     * @param key the key to save the preference as
     * @param value the value to save (Boolean)
     */
    public void save(String key, Boolean value){
        Editor editor;

        editor = this.sharedPrefs.edit();

        editor.putBoolean(key, value);

        editor.commit();
    }

    /**
     * removes the key
     *
     * @param key the key to remove
     */
    public void remove(String key){
        Editor editor;
        editor = this.sharedPrefs.edit();

        editor.remove(key);

        editor.commit();
    }

    /**
     * get the value of a key or a default value as String
     *
     * @param key the key to retrieve
     * @param defaultVal fallback value
     * @return String
     */
    public String get(String key, String defaultVal){
        return this.sharedPrefs.getString(key, defaultVal);
    }

    /**
     * get the value of a key or a default value as Boolean
     *
     * @param key the key to retrieve
     * @param defaultVal fallback value
     * @return Boolean
     */
    public boolean getBoolean(String key, Boolean defaultVal){
        return this.sharedPrefs.getBoolean(key, defaultVal);
    }

    /**
     * get the value of a key as a String Set
     *
     * @param key the key to retrieve
     * @return String Set
     */
    public Set<String> getStringSet(String key){
        return this.sharedPrefs.getStringSet(key, new HashSet<String>());
    }

    /**
     * resets the shared preferences
     */
    public void clearSharedPreference() {
        Editor editor;

        editor = this.sharedPrefs.edit();

        editor.clear();

        editor.commit();
    }


    public int getFB() {
        return fb;
    }

    public void setFB(int fb) {
        this.fb = fb;
    }

    public String getInfoSysURL() {
        return infoSysURL;
    }

    public void setInfoSysURL(String infoSysURL) {
        this.infoSysURL = infoSysURL;
    }

    public String getVPlanURL() {
        return vplanURL;
    }

    public void setVPlanURL(String vplanURL) {
        this.vplanURL = vplanURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
