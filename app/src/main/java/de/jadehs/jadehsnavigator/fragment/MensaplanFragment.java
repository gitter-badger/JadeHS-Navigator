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
package de.jadehs.jadehsnavigator.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.adapter.MensaplanPagerAdapter;
import de.jadehs.jadehsnavigator.database.DBHelper;
import de.jadehs.jadehsnavigator.database.MensaplanDayDataSource;
import de.jadehs.jadehsnavigator.database.MensaplanMealDataSource;
import de.jadehs.jadehsnavigator.response.MensaPlanAsyncResponse;
import de.jadehs.jadehsnavigator.task.ParseMensaplanTask;
import de.jadehs.jadehsnavigator.util.CalendarHelper;
import de.jadehs.jadehsnavigator.util.Preferences;
import de.jadehs.jadehsnavigator.view.MensaplanTabLayout;

public class MensaplanFragment extends Fragment implements MensaPlanAsyncResponse{

    private ParseMensaplanTask asyncTask;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private Preferences preferences;
    private MensaplanMealDataSource mensaplanMealDataSource;
    private MensaplanDayDataSource mensaplanDayDataSource;
    private CalendarHelper calendarWeekHelper = new CalendarHelper();

    private DBHelper dbHelper;
    private int weekNumber;
    private int week = 0;
    private boolean nextWeek = false;
    private boolean mensaplanFirstTime = true;
    private String[] stringList;

    private MensaplanTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    private AlertDialog.Builder builder;

    private String [] tmp;
    private List<String> list;
    private ArrayList<String> arrayList;
    private ArrayList<ArrayList> mensaplanWeeks;
    private AlertDialog alert;

    private String iconText;
    private ArrayAdapter<String> modeAdapter;

    private ListView modeList;

    private boolean needToRefresh= false;


    public MensaplanFragment () {

    }

    /**
     * Called when the fragment's activity has been created and this
     * fragment's view hierarchy instantiated.  It can be used to do final
     * initialization once these pieces are in place, such as retrieving
     * views or restoring state.  It is also useful for fragments that use
     * {@link #setRetainInstance(boolean)} to retain their instance,
     * as this callback tells the fragment when it is fully associated with
     * the new activity instance.  This is called after {@link #onCreateView}
     * and before {@link #onViewStateRestored(Bundle)}.
     *
     * @param savedInstanceState If the fragment is being re-created from
     *                           a previous saved state, this is the state.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        this.weekNumber = calendarWeekHelper.getWeekNumber();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu != null){
            // Eimstellungen aus dem Menü entfernen
            menu.findItem(R.id.action_settings).setVisible(false);
        }

         inflater.inflate(R.menu.fragment_mensaplan, menu);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //return super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.refresh_mensaplan:

                updateMensaplan(true);
                break;
            case R.id.changeWeek_mensaplan:
                if (!nextWeek) {

                    nextWeek = true;
                    this.week = week + 1;
                    updateMensaplan(false);

                    item.setTitle("Zurück zur aktuellen Woche");


                } else {
                    nextWeek = false;
                    item.setTitle("Nächste Woche");
                    this.week = 0;
                    updateMensaplan(false);

                }
                Toast.makeText(getActivity().getApplicationContext(), "Woche gewechselt.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.mensaplan_action_info:
                showDialog();



            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_mensaplan, container, false);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        updateMensaplan(false);
        if(!preferences.getBoolean("readInstruction", false)) {
            try {
                this.builder = new AlertDialog.Builder(getActivity());
                // don't show this dialog again
                preferences.save("readInstruction", true);
                String instructionMsg = String.format(getActivity().getResources().getString(R.string.mensaplan_belehrung), preferences.getLocation());

                builder.setTitle(getActivity().getResources().getString(R.string.mensaplan_belehrung_title));
                builder.setMessage(instructionMsg)
                        // Positiv-Button wird deklariert
                        .setPositiveButton(getActivity().getResources().getString(R.string.mensaplan_belehrung_positivebutton),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(
                                            final DialogInterface dialog,
                                            final int id) {
                                        dialog.dismiss();
                                    }
                                });

                mensaplanFirstTime = false;

                alert = builder.create();

                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }catch (Exception ex){
                Log.wtf("EXXX", "EX", ex);
            }
        }

    }
    public void updateMensaplan(Boolean refreshButtonClicked) {
        this.cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        this.activeNetwork = cm.getActiveNetworkInfo();
        this.preferences = new Preferences(getActivity().getApplicationContext());


        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        openDatabases();
        needToRefresh = this.mensaplanDayDataSource.needToRefresh(weekNumber,preferences.getLocation());

        if ((isConnected && refreshButtonClicked
                || isConnected && needToRefresh))
        {

            if(refreshButtonClicked || needToRefresh){
                mensaplanDayDataSource.deleteMensaplanDay();
                mensaplanMealDataSource.deleteMensaplanDay();
            }

            this.asyncTask = new ParseMensaplanTask(getActivity());
            this.asyncTask.delegate = this;
            this.asyncTask.execute();
        } else if (!needToRefresh) {
            try {
                // Datenquelle öffnen und Einträge abrufen
                mensaplanWeeks= this.mensaplanDayDataSource.getMensaplanDays(preferences.getLocation());

                try{
                    mViewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                processFinish(mensaplanWeeks);

            } catch (Exception ex) {
                ex.printStackTrace();
                Toast.makeText(getActivity(), "Fehler beim Abrufen des Mensaplans", Toast.LENGTH_LONG).show();
            }
            finally {
                closeDatabases();


            }
        } else {
            closeDatabases();
            Toast.makeText(getActivity(), "Keine Internetverbindung vorhanden, Daten konnten nicht aktualisiert werden.", Toast.LENGTH_LONG).show();
            getActivity().findViewById(R.id.progressMensaplan).setVisibility(View.GONE);
            getActivity().findViewById(R.id.errorOverlay).setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void processFinish(ArrayList<ArrayList> items) {
        try{
            mViewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
            getActivity().findViewById(R.id.progressMensaplan).setVisibility(View.GONE);
            mViewPager.setAdapter(new MensaplanPagerAdapter(getActivity(), items, this.week));
            if(week!=1) {
                mViewPager.setCurrentItem(calendarWeekHelper.getDay());
            }
            mSlidingTabLayout = (MensaplanTabLayout) getActivity().findViewById(R.id.sliding_tabs);
            mSlidingTabLayout.setViewPager(mViewPager);

        } catch (Exception e) {
            e.printStackTrace();
            Log.wtf("processFinish","Aktualisierung unterbrochen, View gewechselt");
        }




    }
    public void showDialog () {
        this.builder = new AlertDialog.Builder(getActivity());
        this.stringList  = getActivity().getResources().getStringArray(R.array.mensaplan_additivies);

        // WorkAround wegen eines Bugs im Android XML-Parser https://code.google.com/p/androidsvg/issues/detail?id=29
        list = Arrays.asList(stringList);
        arrayList = new ArrayList<>(list);
        for(int i = 0; i<arrayList.size(); i++) {
            String zusatzstoff = arrayList.get(i);
            //TODO vegan# und co. auslagern
            if(zusatzstoff.startsWith("vegan#")) {
                tmp = zusatzstoff.split("#");

                iconText = "\uD83C\uDF31" + tmp[1];
                arrayList.set(i,iconText);
            }
            if(zusatzstoff.startsWith("rind#")) {
                tmp = zusatzstoff.split("#");
                iconText = "\uD83D\uDC2E" + tmp[1];
                //iconText = "\uD83D\uDC04" + tmp[1];
                arrayList.set(i,iconText);
            }
            if(zusatzstoff.startsWith("gefluegel#")) {
                tmp = zusatzstoff.split("#");
                iconText = "\uD83D\uDC14" + tmp[1];
                arrayList.set(i,iconText);

            }
            if(zusatzstoff.startsWith("schwein#")) {
                tmp = zusatzstoff.split("#");
                //D8 3D DC 37
                iconText = "\uD83D\uDC37" + tmp[1];
                //iconText = "\uD83D\uDC16" + tmp[1];
                arrayList.set(i,iconText);

            }
            if(zusatzstoff.startsWith("vegetarisch#")) {
                tmp = zusatzstoff.split("#");
                iconText = "\uD83C\uDF3D" + tmp[1];
                arrayList.set(i,iconText);

            }
            if(zusatzstoff.startsWith("lamm#")) {
                tmp = zusatzstoff.split("#");
                iconText = "\uD83D\uDC11" + tmp[1];
                arrayList.set(i,iconText);
            }

        }
        stringList = arrayList.toArray(new String[list.size()]);


        modeList = new ListView(getActivity().getApplicationContext());
        modeList.setVerticalScrollBarEnabled(true);
        modeAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.mensaplan_dialog_list_item, R.id.txtMensaplan_dialog_list_item, stringList);
        modeList.setAdapter(modeAdapter);

        builder.setView(modeList);
        builder.setTitle(getActivity().getResources().getString(R.string.mensaplan_zusatzstoffe_title));
        builder.setPositiveButton(getActivity().getResources().getString(R.string.mensaplan_zusatzstoffe_positivebutton), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }

        });
        builder.setCancelable(true);
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void openDatabases () {
        try {
            this.mensaplanMealDataSource = new MensaplanMealDataSource(getActivity().getApplicationContext());
            this.mensaplanMealDataSource.open();
            this.mensaplanDayDataSource = new MensaplanDayDataSource(getActivity().getApplicationContext());
            this.mensaplanDayDataSource.open();
        }
        catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Fehler: Zugriff auf Datenbank nicht möglich.", Toast.LENGTH_SHORT).show();
        }

    }
    public void closeDatabases (){
        this.mensaplanMealDataSource.close();
        this.mensaplanDayDataSource.close();

    }
}