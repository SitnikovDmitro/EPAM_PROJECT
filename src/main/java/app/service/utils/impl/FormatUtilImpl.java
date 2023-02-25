package app.service.utils.impl;

import app.service.utils.FormatUtil;

public class FormatUtilImpl implements FormatUtil {
    @Override
    public String formatPrice(int price) {
        return formatFraction(price, 100);
    }

    @Override
    public String formatAverageGrade(int gradeSum, int gradeNumber) {
        return formatFraction(gradeSum, gradeNumber);
    }

    @Override
    public String formatFraction(int numerator, int denominator) {
        if (denominator == 0) return "Not a number";

        int value = 100*numerator/denominator;
        if (value % 100 == 0) {
            return Integer.toString(value/100);
        } else if (value % 10 == 0) {
            String str = Integer.toString(value/10);
            if (str.length() == 1) str = "0" + str;
            return str.substring(0, str.length()-1)+"."+str.substring(str.length()-1);
        } else {
            String str = Integer.toString(value);
            if (str.length() == 1) str = "0" + str;
            if (str.length() == 2) str = "0" + str;
            return str.substring(0, str.length()-2)+"."+str.substring(str.length()-2);
        }
    }

    @Override
    public int roundAverageGrade(int gradeSum, int gradeNumber) {
        if (gradeNumber == 0) return 0;

        return Math.round((float) gradeSum / gradeNumber);
    }
}
