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

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.jadehs.jadehsnavigator.R;
import de.jadehs.jadehsnavigator.model.MensaplanDay;
import de.jadehs.jadehsnavigator.model.MensaplanMeal;
import de.jadehs.jadehsnavigator.util.CalendarHelper;
import de.jadehs.jadehsnavigator.util.Preferences;

public class MensaplanPagerAdapter extends PagerAdapter {
    private Context context;
    private CalendarHelper calendarWeekHelper = new CalendarHelper();
    private int currentWeek = calendarWeekHelper.getWeekNumber();
    private int week = 0;
    private Preferences preferences;
    private View view;

    private String TAG = "MensaplanPagerAdapter";
    private ArrayList<ArrayList> mensaWeeks = new ArrayList<>();
    private ArrayList<MensaplanDay> mensaplanDays;
    private ArrayList<MensaplanMeal> mensaplanMeals;
    private ArrayList<MensaplanMeal> hauptgerichte;
    private ArrayList<MensaplanMeal> beilagen;
    private ArrayList<MensaplanMeal> salate;
    private ArrayList<MensaplanMeal> zusatzessenArrayList;
    private String tabTitle;

    private TextView zusatzessenTitle;
    private TextView hauptgericht1;
    private TextView hauptgericht1price;
    private TextView hauptgericht1additives;
    private TextView hauptgericht2;
    private TextView hauptgericht2price;
    private TextView hauptgericht2additives;
    private TextView zusatzessen;
    private TextView zusatzessenPrice;
    private TextView zusatzessenAdditives;
    private TextView beilage1;
    private TextView beilage1price;
    private TextView beilage1additives;
    private TextView beilage2;
    private TextView beilage2price;
    private TextView beilage2additives;
    private TextView gemuese;
    private TextView gemuesePrice;
    private TextView gemueseAdditives;
    private TextView salad1;
    private TextView salad1price;
    private TextView salad1additives;
    private TextView salad2;
    private TextView salad2price;
    private TextView salad2additives;
    private TextView suppe;
    private TextView suppePrice;
    private TextView suppeAdditives;
    private TextView dessert;
    private TextView dessertPrice;
    private TextView dessertAdditives;
    private TextView lastUpdateMensa;

    private LayoutInflater mInflater;

    public MensaplanPagerAdapter(Context context, ArrayList<ArrayList> mensaWeeks, int week) {
        this.context = context;
        this.preferences = new Preferences(context);

        this.mensaWeeks = mensaWeeks;
        this.week = week;
    }

    public int typeCounter(int mealType, int day){
        mensaplanDays = mensaWeeks.get(week);
        mensaplanMeals = mensaplanDays.get(day).getMeals();
        int counter = 0;
        for(MensaplanMeal meal: mensaplanMeals){
            if(meal.getType()==mealType) counter++;
        }
        return counter;
    }


    public MensaplanMeal getMeal(int day, int artMeal, boolean erstesGericht) {
        //Log.wtf(TAG,"getMeal wurde aufgerufen.");
        //TODO Methode nochmal gründlich anschauen gegebenfalls verbessern
         /*Bedeutung int Werte:
            #1: Hauptgerichte
            #2: Zusatzessen(Wilhelmshaven)
            #3: Beilagen
            #4: Gemüse
            #5: Salate
            #6: Suppen
            #7: Desserts
        */
        hauptgerichte = new ArrayList<>();
        beilagen = new ArrayList<>();
        salate = new ArrayList<>();
        zusatzessenArrayList = new ArrayList<>();
        mensaplanMeals = new ArrayList<>();
        mensaplanDays = new ArrayList<>();
        mensaplanDays= mensaWeeks.get(week);
        mensaplanMeals = mensaplanDays.get(day).getMeals();

        for(MensaplanMeal meal: mensaplanMeals) {
            if (meal.getType() == artMeal && erstesGericht) {
                return meal;

            }
            if(meal.getType() == 1 && !erstesGericht){
                hauptgerichte.add(meal);
            }
            if(meal.getType() == 3 && !erstesGericht){
                beilagen.add(meal);
            }
            if(meal.getType() == 5 && !erstesGericht) {
                salate.add(meal);

            }

        }

        if(!erstesGericht) {

            switch (artMeal) {
                case 1:

                    if (hauptgerichte.size() > 1) {
                        return hauptgerichte.get(1);
                    }
                    break;
                case 3:
                    if (beilagen.size() > 1) {
                        return beilagen.get(1);
                    }
                    break;
                case 5:
                    if (salate.size() > 1) {
                        return salate.get(1);
                    }
                    break;


            }
        }


        //Log.wtf(TAG,"getMeal: null");
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return o == view;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        tabTitle = "";
        switch(position+1){
            //TODO Tab Titles auslagern falls App auf English gestellt werden soll
            case 1:
                tabTitle =  context.getString(R.string.strWeekdayMonday);
            break;
            case 2:
                tabTitle =  context.getString(R.string.strWeekdayTuesday);
                break;
            case 3:
                tabTitle =  context.getString(R.string.strWeekdayWednesday);
                break;
            case 4:
                tabTitle =  context.getString(R.string.strWeekdayThursday);
                break;
            case 5:
                tabTitle =  context.getString(R.string.strWeekdayFriday);


        }
        return tabTitle;
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // Inflate a new layout from our resources

        mInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);



        view = mInflater.inflate(R.layout.mensaplan_day_item, container, false);
        // Add the newly created View to the ViewPager
        container.addView(view);


        // Retrieve a TextView from the inflated View, and update it's text
        //TODO Irgendwie anders lösen!
        if(!preferences.getLocation().equals(context.getResources().getString(R.string.bez_WHV))) {
            zusatzessenTitle = (TextView) view.findViewById(R.id.titleZusatzessen);
            zusatzessenTitle.setText("Pasta");
        }

        hauptgericht1 = (TextView) view.findViewById(R.id.textHauptgericht1);
        hauptgericht1price = (TextView) view.findViewById(R.id.textHauptgericht1price);
        hauptgericht1additives = (TextView) view.findViewById(R.id.textHauptgericht1additives);

        hauptgericht2 = (TextView) view.findViewById(R.id.textHauptgericht2);
        hauptgericht2price = (TextView) view.findViewById(R.id.textHauptgericht2Price);
        hauptgericht2additives = (TextView) view.findViewById(R.id.textHauptgericht2additives);

        zusatzessen = (TextView) view.findViewById(R.id.textZusatzessen);
        zusatzessenPrice = (TextView) view.findViewById(R.id.textZusatzessenPrice);
        zusatzessenAdditives = (TextView) view.findViewById(R.id.textZusatzessenAdditives);

        beilage1 = (TextView) view.findViewById(R.id.textBeilage1);
        beilage1price = (TextView) view.findViewById(R.id.textBeilage1price);
        beilage1additives = (TextView) view.findViewById(R.id.textBeilage1Additives);

        beilage2 = (TextView) view.findViewById(R.id.textBeilage2);
        beilage2price = (TextView) view.findViewById(R.id.textBeilage2price);
        beilage2additives = (TextView) view.findViewById(R.id.textBeilage2additives);


        gemuese = (TextView) view.findViewById(R.id.textGemuese);
        gemuesePrice = (TextView) view.findViewById(R.id.textGemuesePrice);
        gemueseAdditives = (TextView) view.findViewById(R.id.textGemueseAdditives);


        salad1 = (TextView) view.findViewById(R.id.textSalad1);
        salad1price = (TextView) view.findViewById(R.id.textSalad1Price);
        salad1additives = (TextView) view.findViewById(R.id.textSalad1Additives);

        salad2 = (TextView) view.findViewById(R.id.textSalad2);
        salad2price = (TextView) view.findViewById(R.id.textSalad2Price);
        salad2additives = (TextView) view.findViewById(R.id.textSalad2Additives);

        suppe = (TextView) view.findViewById(R.id.textSuppen);
        suppePrice = (TextView) view.findViewById(R.id.textSuppenPrice);
        suppeAdditives = (TextView) view.findViewById(R.id.textSuppenAdditives);


        dessert = (TextView) view.findViewById(R.id.textDessert);
        dessertPrice = (TextView) view.findViewById(R.id.textDessertPrice);
        dessertAdditives = (TextView) view.findViewById(R.id.textDessertAdditives);

        lastUpdateMensa = (TextView) view.findViewById(R.id.textLastUpdateMensa);

        try {
            if (getMeal(position, 2, true).getDescription().equals("")) {
                zusatzessen.setHeight(0);
                zusatzessenPrice.setHeight(0);
                zusatzessenAdditives.setHeight(0);
            } else {
                zusatzessen.setText(getMeal(position, 2, true).getDescription());
                zusatzessenPrice.setText(getMeal(position, 2, true).getPrice());
                if (getMeal(position, 2, true).getAdditives().equals("")) {
                    zusatzessenAdditives.setHeight(0);
                }
                {
                    zusatzessenAdditives.setText(getMeal(position, 2, true).getAdditives());
                }

            }

            hauptgericht1.setText(getMeal(position, 1, true).getDescription());
            hauptgericht1price.setText(getMeal(position, 1, true).getPrice());
            if (getMeal(position, 1, true).getAdditives().equals("")) {
                hauptgericht1additives.setHeight(0);
            } else {
                hauptgericht1additives.setText(getMeal(position, 1, true).getAdditives());
            }

            hauptgericht2.setText(getMeal(position, 1, false).getDescription());
            hauptgericht2price.setText(getMeal(position, 1, false).getPrice());
            if (getMeal(position, 1, false).getAdditives().equals("")) {
                hauptgericht2additives.setHeight(0);
            } else {
                hauptgericht2additives.setText(getMeal(position, 1, false).getAdditives());
            }

            beilage1.setText(getMeal(position, 3, true).getDescription());
            beilage1price.setText(getMeal(position, 3, true).getPrice());
            if (getMeal(position, 3, true).getAdditives().equals("")) {
                beilage1additives.setHeight(0);
            } else {
                beilage1additives.setText(getMeal(position, 3, true).getAdditives());
            }


            if(typeCounter(3, position) > 1) {
                beilage2.setText(getMeal(position, 3, false).getDescription());
                beilage2price.setText(getMeal(position, 3, false).getPrice());
                if (getMeal(position, 3, false).getAdditives().equals("")) {
                    beilage2additives.setHeight(0);
                } else {
                    beilage2additives.setText(getMeal(position, 3, false).getAdditives());
                }
            }


            gemuese.setText(getMeal(position, 4, true).getDescription());
            gemuesePrice.setText(getMeal(position, 4, true).getPrice());
            if (getMeal(position, 4, true).getAdditives().equals("")) {
                gemueseAdditives.setHeight(0);
            } else {
                gemueseAdditives.setText(getMeal(position, 4, true).getAdditives());
            }


            salad1.setText(getMeal(position, 5, true).getDescription());
            salad1price.setText(getMeal(position, 5, true).getPrice());
            if (getMeal(position, 5, true).getAdditives().equals("")) {
                salad1additives.setHeight(0);
            } else {
                salad1additives.setText(getMeal(position, 5, true).getAdditives());
            }


            if(typeCounter(5, position) > 1) {
                view.findViewById(R.id.gridLayout7).setVisibility(View.VISIBLE);
                salad2.setText(getMeal(position, 5, false).getDescription());
                salad2price.setText(getMeal(position, 5, false).getPrice());
                if (getMeal(position, 5, false).getAdditives().equals("")) {
                    salad2additives.setHeight(0);
                } else {
                    salad2additives.setText(getMeal(position, 5, false).getAdditives());
                }
            }else{
                view.findViewById(R.id.gridLayout7).setVisibility(View.GONE);
            }


            suppe.setText(getMeal(position, 6, true).getDescription());
            suppePrice.setText(getMeal(position, 6, true).getPrice());
            if (getMeal(position, 6, true).getAdditives().equals("")) {
                suppeAdditives.setHeight(0);
            } else {
                suppeAdditives.setText(getMeal(position, 6, true).getAdditives());
            }


            dessert.setText(getMeal(position, 7, true).getDescription());
            dessertPrice.setText(getMeal(position, 7, true).getPrice());
            if (getMeal(position, 7, true).getAdditives().equals("")) {
                dessertAdditives.setHeight(0);
            } else {
                dessertAdditives.setText(getMeal(position, 7, true).getAdditives());
            }


            //TODO String auslagern
            lastUpdateMensa.setText("Plan für KW: " + mensaplanDays.get(position).getWeekNumber() + "  |  Abgerufen am: " + mensaplanDays.get(position).getCreated());
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            view.findViewById(R.id.scrollView).setVisibility(View.GONE);
            view.findViewById(R.id.footer).setVisibility(View.GONE);
            view.findViewById(R.id.errorOverlay).setVisibility(View.VISIBLE);
        }

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}