package de.IM.Smash.Util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static de.IM.Smash.Util.TypeUtils.isInt;


public class TimeUtils {
    public static Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Timestamp getTimestampForDays(int days) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(System.currentTimeMillis());
        currentDate.add(Calendar.DATE, days);
        return new Timestamp(currentDate.getTimeInMillis());
    }

    public static Timestamp getTimestampForDays(int days, Timestamp base) {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTimeInMillis(base.getTime());
        currentDate.add(Calendar.DATE, days);
        return new Timestamp(currentDate.getTimeInMillis());
    }

    public static Timestamp getTimestampForVariable(String var) {
        Calendar result = Calendar.getInstance();
        result.setTimeInMillis(System.currentTimeMillis());
        if (isInt(var)) {
            result.add(Calendar.SECOND, Integer.parseInt(var));
            return new Timestamp(result.getTimeInMillis());
        }
        String time = var.substring(0, var.length() - 1);
        if (isInt(time)) {
            if (var.endsWith("s")) {
                result.add(Calendar.SECOND, Integer.parseInt(time));
                return new Timestamp(result.getTimeInMillis());
            } else if (var.endsWith("m")) {
                result.add(Calendar.MINUTE, Integer.parseInt(time));
                return new Timestamp(result.getTimeInMillis());
            } else if (var.endsWith("h")) {
                result.add(Calendar.HOUR, Integer.parseInt(time));
                return new Timestamp(result.getTimeInMillis());
            } else if (var.endsWith("d")) {
                result.add(Calendar.DATE, Integer.parseInt(time));
                return new Timestamp(result.getTimeInMillis());
            }
        }
        throw new IllegalArgumentException("Invalid parameter " + var);
    }

    public static String convertTimestamp(Timestamp time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.format(cal.getTime());
    }

    public static String formatTime(long millis) {
        String s = "";
        int seconds = (int) (millis / 1000L);
        if (seconds <= 0) return "PERMANENT";
        int units = 0;
        if (seconds / (60.0 * 60.0 * 24 * 365) > 1 && units < 3) {
            s += (int) (seconds / (60.0 * 60.0 * 24 * 365)) + " " + "Jahre" + " ";
            seconds %= (60.0 * 60.0 * 24 * 365);
            units++;
        }
        if (seconds / (60.0 * 60.0 * 24 * 30) > 1 && units < 3) {
            s += (int) (seconds / (60.0 * 60.0 * 24 * 30)) + " " + "Monate" + " ";
            seconds %= (60.0 * 60.0 * 24 * 30);
            units++;
        }
        if (seconds / (60.0 * 60.0 * 24 * 7) > 1 && units < 3) {
            s += (int) (seconds / (60.0 * 60.0 * 24 * 7)) + " " + "Wochen" + " ";
            seconds %= (60.0 * 60.0 * 24 * 7);
            units++;
        }
        if (seconds / (60.0 * 60.0 * 24) > 1 && units < 3) {
            s += (int) (seconds / (60.0 * 60.0 * 24)) + " " + "Tage" + " ";
            seconds %= (60.0 * 60.0 * 24);
            units++;
        }
        if ((seconds / (60.0 * 60.0)) > 1 && units < 3) {
            s += (int) (seconds / (60.0 * 60.0)) + " " + "Stunden" + " ";
            seconds %= (60.0 * 60.0);
            units++;
        }
        if ((seconds / 60.0) > 1 && units < 3) {
            s += (int) (seconds / (60.0 * 60.0)) + " " + "Minuten" + " ";
            seconds %= 60;
            units++;
        }
        if (seconds  > 0 && units < 3) {
            s += (int) (seconds / (60.0 * 60.0)) + " " + "Sekunden" + " ";
            units++;
        }
        return s;
    }

    public static String buildTimeString(int time, int digits) {
        String output = "";
        int hour = time / 3600;
        int minute = time % 3600 / 60;
        int second = time % 60;
        output = hour >= 10 ? output.concat("" + hour) : output.concat("0" + hour);
        output = minute >= 10 ? output.concat(":" + minute) : output.concat(":0" + minute);
        output = second >= 10 ? output.concat(":" + second) : output.concat(":0" + second);
        if (digits == -1) {
            while (output.length() > 4 && (output.charAt(0) == 48 || output.charAt(0) == 58)) {
                output = output.substring(1);
            }
        } else {
            output = output.substring(output.length() - digits);
        }

        return output;
    }


}
