package app.service.utils;

/**
 * Provides format utilities
 **/
public interface FormatUtil {
    /**
     * Formats price
     * @param price price in coins
     * @return string representation of given price in grivnas
     **/
    String formatPrice(int price);

    /**
     * Formats book average grade
     * @param gradeSum sum of all grades on book
     * @param gradeNumber number of all grades on book
     * @return string representation of average grade on a book
     **/
    String formatAverageGrade(int gradeSum, int gradeNumber);

    /**
     * Formats fraction
     * @param numerator fraction numerator
     * @param denominator fraction denominator
     * @return string decimal representation of fraction
     **/
    String formatFraction(int numerator, int denominator);

    /**
     * Rounds average grade
     * @param gradeSum sum of grades
     * @param gradeNumber number of grades
     * @return closest integer value to real average grade value
     **/
    int roundAverageGrade(int gradeSum, int gradeNumber);
}
