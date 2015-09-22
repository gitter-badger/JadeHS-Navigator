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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.sql.SQLException;
import java.util.ArrayList;

import de.jadehs.jadehsnavigator.model.MensaplanDay;

/**
 * Created by HendrikKremer on 26.07.15.
 */
public class MensaplanDayDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private MensaplanMealDataSource mensaplanMealDataSource;

    private String[] allColumns = {dbHelper.COLUMN_ID,
                                dbHelper.COLUMN_DAY,
                                dbHelper.COLUMN_WEEKNUMBER,
                                dbHelper.COLUMN_WEEK,
                                dbHelper.COLUMN_LOCATION,
                                dbHelper.COLUMN_CREATED};

    public final String DB_TABLE = dbHelper.TABLE_MENSAPLANDAY;

        public MensaplanDayDataSource(Context context){
            dbHelper = new DBHelper(context);
            mensaplanMealDataSource = new MensaplanMealDataSource(context);
        }

        public void open() throws SQLException {
            this.database = dbHelper.getWritableDatabase();
        }

        public void close(){
            dbHelper.close();
        }



        public long createMensaplanDAY(MensaplanDay mensaplanDAY) {

            ContentValues values = new ContentValues();
            values.put(dbHelper.COLUMN_DAY, mensaplanDAY.getDay());
            values.put(dbHelper.COLUMN_WEEKNUMBER, mensaplanDAY.getWeekNumber());
            values.put(dbHelper.COLUMN_WEEK, mensaplanDAY.getWeek());
            values.put(dbHelper.COLUMN_LOCATION, mensaplanDAY.getLocation());
            values.put(dbHelper.COLUMN_CREATED, mensaplanDAY.getCreated());

            long insertID = this.database.insert(this.DB_TABLE, null, values);
            return insertID;
        }


        public void deleteMensaplanDay(){
            // @todo: debug-msg löschen
            this.database.delete(this.DB_TABLE, dbHelper.COLUMN_ID+"> 0", null);
        }

        public ArrayList<ArrayList> getMensaplanDays(String location){
            ArrayList<ArrayList> bothWeeks = new ArrayList<>();
            bothWeeks.add(getMensaplanWeek(location, 0));
            bothWeeks.add(getMensaplanWeek(location, 1));


            return bothWeeks;
        }

        private ArrayList<MensaplanDay> getMensaplanWeek (String location , int week){
            ArrayList<MensaplanDay> mensaplanWeek = new ArrayList<>();
            Cursor cursor = database.query(this.DB_TABLE, allColumns, "week= ? and location = ?",new String[] {""+week,location}, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                MensaplanDay mensaplanDay = cursorToMensaplanDay(cursor);
                mensaplanWeek.add(mensaplanDay);

                cursor.moveToNext();
            }
            cursor.close();
            return mensaplanWeek;
        }
        public boolean exists(String fieldName, String fieldValue) {
            String Query = "Select * from " + this.DB_TABLE + " where " + fieldName + " = '" + fieldValue + "'";
            Cursor cursor = this.database.rawQuery(Query, null);
            if(cursor.getCount() <= 0){
                cursor.close();
                return false;
            }
            cursor.close();
            return true;
        }
    public boolean exists(String fieldName, int fieldValue) {
        String Query = "Select * from " + this.DB_TABLE + " where " + fieldName + " = " + fieldValue ;
        Cursor cursor = this.database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
    private MensaplanDay cursorToMensaplanDay(Cursor cursor){
            MensaplanDay mensaplanDay = new MensaplanDay();

            mensaplanDay.setId(cursor.getLong(0));
            mensaplanDay.setDay(cursor.getInt(1));
            mensaplanDay.setWeekNumber(cursor.getInt(2));
            mensaplanDay.setWeek(cursor.getInt(3));
            mensaplanDay.setLocation(cursor.getString(4));
            mensaplanDay.setCreated(cursor.getString(5));
            mensaplanDay.setMeals(mensaplanMealDataSource.getAllMensaplanMealsDay(mensaplanDay.getId()));

            return  mensaplanDay;
        }
        public boolean needToRefresh(int weekNumber, String location) {
            boolean locationExists = exists(dbHelper.COLUMN_LOCATION,location);
            boolean weeknumberExists = exists(dbHelper.COLUMN_WEEKNUMBER, weekNumber);
                if (locationExists && weeknumberExists) {

                    String Query = "SELECT * FROM " + this.DB_TABLE + " WHERE " + dbHelper.COLUMN_LOCATION + " = '" + location + "' ORDER BY " + dbHelper.COLUMN_WEEKNUMBER + " ASC LIMIT 1";

                    Cursor cursor = this.database.rawQuery(Query, null);
                    cursor.moveToFirst();
                    Log.wtf("Database WeekNumber", "" + cursor.getInt(2));
                    Log.wtf("Current Weeknumber", "" + weekNumber);
                            if (!(cursor.getInt(2) == weekNumber)) {
                                //TODO Jahreswechsel
                                Log.wtf("needToRefresh", "Alte Daten mussten aktuallisiert werden.");
                                cursor.close();
                                return true;
                            } else {
                                cursor.close();
                                return false;
                            }
                }
            return true;
        }

}
