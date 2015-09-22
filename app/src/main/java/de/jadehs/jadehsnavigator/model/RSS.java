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
package de.jadehs.jadehsnavigator.model;

import android.content.Context;
import android.util.Log;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RSS {
    private Context context;
    private String lastUpdate;
    private ArrayList<RSSItem> rssItems = null;

    private RSSOrigin origin;
    private ArrayList<RSSOrigin> origins;

    public RSS(Context context, RSSOrigin origin){
        this.context = context;
        this.origin = origin;
    }

    public RSS(Context context, ArrayList<RSSOrigin> origins){
        this.context = context;
        this.origins = origins;
    }

    public boolean read(){
        this.rssItems = new ArrayList<RSSItem>();

        try {
            //InfoSysItemDataSource infoSysItemDataSource = new InfoSysItemDataSource(this.context);
            //infoSysItemDataSource.open();

            RSSItem rssItem;

            Document doc = Jsoup.connect(this.origin.getUrl())
                    .parser(Parser.xmlParser())
                    .get();
            //Log.i("PARSING TAG", "Done Parsing");

            for (Element item : doc.select("item")) {
                String title = item.select("title").first().text();
                String description = item.select("description").first().text();
                String link = item.select("link").first().text();
                String pubDate = item.select("pubDate").first().text();

                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                Date date = null;
                String dateStr = "";
                try{
                    date = sdf.parse(pubDate);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    // Set date string
                    dateStr =   cal.get(Calendar.DAY_OF_MONTH) + "." + cal.get(Calendar.MONTH) + "." + cal.get(Calendar.YEAR) + " - " +
                                cal.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", cal.get(Calendar.MINUTE));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                //@todo: pubDate 7 Tage vllt? Studentenwerk geht zurück bis Novemeber 14...

                //infoSysItem = new InfoSysItem(title,description,link,creator,created,this.fb);
                //infoSysItemDataSource.createInfoSysItem(infoSysItem);

                //this.infoSysItems.add(infoSysItem);

                rssItem = new RSSItem(title, description, link, this.origin.getID(), dateStr);
                this.rssItems.add(rssItem);
                //Log.wtf("RSS FROM ID: " + this.origin.getID(), rssItem.getTitle() + "(" + rssItem.getLink() + ")");

                //Log.i("ITEM TAG", "ADDED ITEM");
            }
            return true;
            //infoSysItemDataSource.close();
        }catch (HttpStatusException httpEx){
            //Log.wtf("HTTP ERROR", "SERVER UNREACHABLE", httpEx);
            return false;
        }catch (Exception ex){
            //Log.wtf("PARSING ERROR", "PARSING FAILED", ex);
            return false;
        }
    }

    public boolean readAll(){
        this.rssItems = new ArrayList<RSSItem>();


        for(RSSOrigin origin : this.origins) {
            try {
                Log.wtf("RSS", "PARSING: " + origin.getTitle());
                //InfoSysItemDataSource infoSysItemDataSource = new InfoSysItemDataSource(this.context);
                //infoSysItemDataSource.open();

                RSSItem rssItem;

                Document doc = Jsoup.connect(origin.getUrl())
                        .parser(Parser.xmlParser())
                        .get();
                //Log.i("PARSING TAG", "Done Parsing");

                for (Element item : doc.select("item")) {
                    String title = item.select("title").first().text();
                    String description = item.select("description").first().text();
                    String link = item.select("link").first().text();
                    String pubDate = item.select("pubDate").first().text();

                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
                    Date date;
                    String dateStr = "";
                    try {
                        date = sdf.parse(pubDate);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(date);
                        // Set date string
                        dateStr = cal.get(Calendar.DAY_OF_MONTH) + "." + (cal.get(Calendar.MONTH)+1) + "." + cal.get(Calendar.YEAR) + "   " +
                                cal.get(Calendar.HOUR_OF_DAY) + ":" + String.format("%02d", cal.get(Calendar.MINUTE));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    //infoSysItem = new InfoSysItem(title,description,link,creator,created,this.fb);
                    //infoSysItemDataSource.createInfoSysItem(infoSysItem);

                    //this.infoSysItems.add(infoSysItem);

                    rssItem = new RSSItem(title, description, link, origin, dateStr);
                    this.rssItems.add(rssItem);
                    //Log.wtf("RSS FROM ID: " + this.origin.getID(), rssItem.getTitle() + "(" + rssItem.getLink() + ")");

                    //Log.i("ITEM TAG", "ADDED ITEM");
                }

                //infoSysItemDataSource.close();
            } catch (HttpStatusException httpEx) {
                Log.wtf("HTTP ERROR", "SERVER UNREACHABLE", httpEx);
            } catch (Exception ex) {
                Log.wtf("PARSING ERROR", "PARSING FAILED", ex);
            }
        }

        return true;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public ArrayList<RSSItem> getRssItems() {
        return rssItems;
    }

    public void setRssItems(ArrayList<RSSItem> rssItems) {
        this.rssItems = rssItems;
    }

    public RSSOrigin getOrigin() {
        return origin;
    }

    public void setOrigin(RSSOrigin origin) {
        this.origin = origin;
    }
}
