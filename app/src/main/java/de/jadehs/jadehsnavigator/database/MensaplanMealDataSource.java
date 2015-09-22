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

import java.sql.SQLException;
import java.util.ArrayList;

import de.jadehs.jadehsnavigator.model.MensaplanMeal;

/**
 * Created by HendrikKremer on 12.08.15.
 */
public class MensaplanMealDataSource {private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] allColumns = {dbHelper.COLUMN_ID,
            dbHelper.COLUMN_TYPE,
            dbHelper.COLUMN_DESCRIPTION,
            dbHelper.COLUMN_PRICE,
            dbHelper.COLUMN_FOOD_ADDITIVES,
            dbHelper.COLUMN_DAYID};
    private ArrayList<MensaplanMeal> mensaplanMeals;



    public final String DB_TABLE = dbHelper.TABLE_MENSAPLANMEAL;

    public MensaplanMealDataSource(Context context){
        dbHelper = new DBHelper(context);

    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }
    public long createMensaplanMeal(MensaplanMeal mensaplanMeal){
            ContentValues values = new ContentValues();
            // Werte einsetzen
            values.put(dbHelper.COLUMN_TYPE, mensaplanMeal.getType());
            values.put(dbHelper.COLUMN_DESCRIPTION, mensaplanMeal.getDescription());
            values.put(dbHelper.COLUMN_PRICE, mensaplanMeal.getPrice());
            values.put(dbHelper.COLUMN_FOOD_ADDITIVES, mensaplanMeal.getAdditives());
            values.put(dbHelper.COLUMN_DAYID, mensaplanMeal.getDayID());

        long insertID = this.database.insert(dbHelper.TABLE_MENSAPLANMEAL, null, values);
            return insertID;

    }

    public void deleteMensaplanMeal(MensaplanMeal mensaplanMeal){
        long id = mensaplanMeal.getId();
        // @todo: debug-msg löschen
        System.out.println("DEBUG: Deleted InfoSysItem: " + id);
        this.database.delete(dbHelper.TABLE_MENSAPLANMEAL, dbHelper.COLUMN_ID + " = " + id, null);
    }

    public ArrayList<MensaplanMeal> getAllMensaplanMealsDay(Long dayID){
        mensaplanMeals = new ArrayList<>();
        try{
            //TODO Überdenken ob es auch noch anders geht
            this.database = dbHelper.getReadableDatabase();
        } catch (Exception e){
           e.printStackTrace();
        }
        Cursor cursor = database.query(dbHelper.TABLE_MENSAPLANMEAL, allColumns, "dayid =" + dayID, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){

            MensaplanMeal mensaplanMeal = cursorToMensaplanMeal(cursor);
                    mensaplanMeals.add(mensaplanMeal);
            cursor.moveToNext();
        }
        cursor.close();
        return mensaplanMeals;
    }
    public void deleteMensaplanDay(){
        // @todo: debug-msg löschen
        this.database.delete(dbHelper.TABLE_MENSAPLANMEAL, dbHelper.COLUMN_ID + "> 0", null);
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
        String Query = "Select * from " + this.DB_TABLE + " where " + fieldName + " = '" + fieldValue + "'";
        Cursor cursor = this.database.rawQuery(Query, null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    private MensaplanMeal cursorToMensaplanMeal(Cursor cursor){
        MensaplanMeal mensaplanMeal = new MensaplanMeal();

        mensaplanMeal.setId(cursor.getLong(0));
        mensaplanMeal.setType(cursor.getInt(1));
        mensaplanMeal.setDescription(cursor.getString(2));
        mensaplanMeal.setPrice(cursor.getString(3));
        mensaplanMeal.setAdditives(cursor.getString(4));
        mensaplanMeal.setDayID(cursor.getLong(5));



        return  mensaplanMeal;
    }

}
