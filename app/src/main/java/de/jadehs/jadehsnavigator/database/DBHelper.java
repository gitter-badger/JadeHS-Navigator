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
package de.jadehs.jadehsnavigator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by re1015 on 22.07.2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    /**
     * Name der Datenbank im Filesystem
     */
    public static final String DATEBASE_NAME = "jhsnav.db";

    /**
     * Version der Datenbank
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Tables
     */
    public static final String TABLE_INFOSYSITEMS = "infosysitems";
    public static final String TABLE_MENSAPLANDAY = "mensaplanday";
    public static final String TABLE_MENSAPLANMEAL = "mensaplanmeal";
    public static final String TABLE_VPLANITEMS = "vplan";

    /**
     * Columns
     */
    // InfoSys
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_LINK = "link";
    public static final String COLUMN_CREATOR = "creator";
    public static final String COLUMN_CREATED = "created";
    public static final String COLUMN_FB = "fb";

    // Columns für MensaplanDay
    public static final String COLUMN_DAY = "day";
    public static final String COLUMN_WEEKNUMBER = "weeknumber";
    public static final String COLUMN_WEEK = "week";
    public static final String COLUMN_LOCATION= "location";

    // Columns for MensaplanMeal
    public static final String COLUMN_TYPE = "type";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_FOOD_ADDITIVES = "additives";
    public static final String COLUMN_DAYID = "dayid";



    // VPlan
    public static final String COLUMN_VPLAN_ID = "_id";
    public static final String COLUMN_VPLAN_START = "start";
    public static final String COLUMN_VPLAN_END = "end";
    public static final String COLUMN_VPLAN_TITLE = "titel";
    public static final String COLUMN_VPLAN_PROF = "prof";
    public static final String COLUMN_VPLAN_ROOM = "room";
    public static final String COLUMN_VPLAN_DAY_OF_WEEK = "weekday";
    public static final String COLUMN_VPLAN_STUDIENGANG_ID = "studiengangid";
    public static final String COLUMN_VPLAN_FB = "fb";
    /**
     * SQL für die Erstellung der Tabellen
     */
    // InfoSys
    private static final String DATABASE_INFOSYSITEMS = "create table " + TABLE_INFOSYSITEMS + " (" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TITLE + " text not null, "
            + COLUMN_DESCRIPTION + " text not null, "
            + COLUMN_LINK + " text not null, "
            + COLUMN_CREATOR + " text not null, "
            + COLUMN_CREATED + " text not null, "
            + COLUMN_FB + " integer not null);";

    private static final String DATABASE_MENSAPLANDAY = "create table " + TABLE_MENSAPLANDAY + " (" + COLUMN_ID + " integer  primary key autoincrement, "
            + COLUMN_DAY + " integer, "
            + COLUMN_WEEKNUMBER + " integer,"
            + COLUMN_WEEK + " integer , "
            + COLUMN_LOCATION + " text,"
            + COLUMN_CREATED + " text );";

    private static final String DATABASE_MENSAPLANMEAL = "create table " + TABLE_MENSAPLANMEAL + " (" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_TYPE + " integer , "
            + COLUMN_DESCRIPTION + " text ,"
            + COLUMN_PRICE + " text , "
            + COLUMN_FOOD_ADDITIVES + " text ,"
            + COLUMN_DAYID + " integer);";

    // VPlan
    private static final String DATABASE_VPLANITEMS = "create table " + TABLE_VPLANITEMS + " (" + COLUMN_VPLAN_ID + " integer primary key autoincrement, "
            + COLUMN_VPLAN_TITLE + " text not null, "
            + COLUMN_VPLAN_PROF + " text not null, "
            + COLUMN_VPLAN_ROOM + " text not null, "
            + COLUMN_VPLAN_START + " text not null, "
            + COLUMN_VPLAN_END + " text not null, "
            + COLUMN_VPLAN_DAY_OF_WEEK + " text not null, "
            + COLUMN_VPLAN_STUDIENGANG_ID + " text not null, "
            + COLUMN_VPLAN_FB + " text not null);";

    public DBHelper(Context context){
        super(context, DATEBASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_INFOSYSITEMS);
        db.execSQL(DATABASE_MENSAPLANDAY);
        db.execSQL(DATABASE_MENSAPLANMEAL);
        db.execSQL(DATABASE_VPLANITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // @todo: upgrade db, delete old data
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_INFOSYS);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_VPLAN);
        // etc..
    }

    public void reset(){
        if (this.context.deleteDatabase(DATEBASE_NAME)) {
            Log.d("DB", "deleteDatabase(): database deleted.");
        } else {
            Log.d("DB", "deleteDatabase(): database NOT deleted.");
        }
    }

}
