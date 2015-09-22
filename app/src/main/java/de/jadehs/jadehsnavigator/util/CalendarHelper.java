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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarHelper {
    Calendar c = Calendar.getInstance();
    Date date = c.getTime();


    public CalendarHelper(){}

    public int getDay(){
        int day = this.c.get(Calendar.DAY_OF_WEEK);
        int setTab = 0;

        switch (day) {
            case Calendar.TUESDAY:
                setTab = 1;
                break;
            case Calendar.WEDNESDAY:
                setTab = 2;
                break;
            case Calendar.THURSDAY:
                setTab = 3;
                break;
            case Calendar.FRIDAY:
                setTab = 4;
                break;
            case Calendar.SATURDAY:
                setTab = 5;
                break;
        }

        return setTab;
    }

    public String getDateRightNow(boolean withSeconds) {
        if(withSeconds){
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy H:mm:ss");
            String formattedDate = sdf.format(date);
            return formattedDate;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy H:mm");
            String formattedDate = sdf.format(date);
            return formattedDate;
        }
    }
    public int getWeekNumber () {
        return c.get(Calendar.WEEK_OF_YEAR);
    }
}
