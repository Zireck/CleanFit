package com.zireck.projectk.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Zireck on 14/07/2015.
 */
public class DateUtils {
    public static String getFormattedMealDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static String getFormattedDayDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(date);
    }

    public static int calculateAge(Date birthDate) {
        int years = 0;
        int months = 0;
        int days = 0;

        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);

        int currentMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        months = currentMonth - birthMonth;

        if (months < 0) {
            years--;
            months = 12 - birthMonth + currentMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
                months--;
            }
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }

        // Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE)) {
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        } else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }

        return years;
    }
}
