/**
 * GCM WILL BE IMPLEMENTED AT A LATER DATE
 *
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

package de.jadehs.jadehsnavigator.service;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.util.Preferences;

public class RegistrationIntentService extends IntentService{

    private static final String TAG = "RegistrationIntentService";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService(){
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.wtf(TAG, "Starte Intent...");
        Preferences preferences = new Preferences(this);

        try{
            synchronized (TAG){
                InstanceID instanceID = InstanceID.getInstance(this);
                String token = instanceID.getToken(getString(R.string.gcm_defaultSenderId), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);

                Log.wtf(TAG, "My Token: " + token);

                sendRegistrationToServer(token);
                subscribeTopics(token);

                // speichere TRUE um anzugeben, dass bereits ein Gcm-Token existiert und dieser Schritt nicht wiederholt werden muss.
                preferences.save(Preferences.SENT_TOKEN_TO_SERVER, true);
            }
        }catch (Exception ex){
            Log.wtf(TAG, "Failed to refresh token", ex);

            // speichere FALSE um anzugeben, dass kein Gcm-Token existiert und dieser Schritt wiederholt werden muss.
            preferences.save(Preferences.SENT_TOKEN_TO_SERVER, false);
        }

        Log.wtf(TAG, "Sende Broadcast...");
        Intent registrationComplete = new Intent(Preferences.REGISTRATION_COMPLETE);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }

    private void sendRegistrationToServer(String token){
        // @todo
    }

    private void subscribeTopics(String token) throws IOException{
        for(String topic : TOPICS){
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
}
 */
