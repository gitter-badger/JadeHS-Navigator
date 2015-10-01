package de.jadehs.jadehsnavigator.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by HendrikKremer on 06.08.15.
 */
public class CalendarHelper {
    Calendar c;
    Date date;

    public CalendarHelper(){
        this.c = Calendar.getInstance();
        this.date = c.getTime();
    }
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

            String formattedDate = sdf.format(this.date);
            return formattedDate;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy H:mm");

            String formattedDate = sdf.format(this.date);
            return formattedDate;
        }
    }
    public int getWeekNumber () {
        return this.c.get(Calendar.WEEK_OF_YEAR);
    }
}
