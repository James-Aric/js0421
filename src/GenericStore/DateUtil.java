package GenericStore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Logger;

/**
 * Utility used to do all calculations between two dates.
 * Log messages are unfortunately necessary due to Field Manipulation noted in java.util.Calendar documentation
 * (Values set by using .set() are not updated unless .get() or other getters are used)
 */
public final class DateUtil {
    //Logger to log Calendar information in order to update values set using .set()
    private static final Logger log = Logger.getGlobal();

    /**
     * Find the number of weekdays given a start date and a number of days. Removes holidays from the resulting value
     * @param checkoutDateString - start date
     * @param rentalDays - number of days
     * @return number of weekdays between the checkoutDateString and the date resulting from adding the rentalDays
     */
    public static int weekdaysBetweenPeriod(String checkoutDateString, int rentalDays){
        int holidays = containsHolidays(checkoutDateString, rentalDays);
        int weekends = numberOfWeekendDays(checkoutDateString, rentalDays);


        return rentalDays - holidays - weekends;
    }

    /**
     * Returns the number of holidays given a start date and a number of days. Removes holidays from the resulting value
     * @param checkoutDateString - start date
     * @param rentalDays - number of days
     * @return number of holidays between the checkoutDateString and the date resulting from adding the rentalDays
     */
    public static int containsHolidays(String checkoutDateString, int rentalDays){
        Calendar checkoutDate = getCalendarFromString(checkoutDateString);

        //Mildly gross way of seeing if the rental period spans multiple years
        //Chose to do it this way as it seemed to be the most consistent way to check, especially in cases
        //where the start date is 12/30/xx and the rentalDays are > 2.
        Calendar returnDate = (Calendar) checkoutDate.clone();
        returnDate.add(Calendar.DAY_OF_YEAR, rentalDays);
        int yearsProgressed = returnDate.get(Calendar.YEAR) - checkoutDate.get(Calendar.YEAR);

        return holidaysBetweenPeriod(checkoutDateString, rentalDays, yearsProgressed);
    }

    /**
     * Returns the number of holidays given a start date and a number of days. Removes holidays from the resulting value
     * @param checkoutDateString - start date in Calendar object form
     * @param rentalDays - number of days
     * @param yearsProgressed - number of years between the start and resulting end date
     * @return number of holidays between the checkoutDateString and the date resulting from adding the rentalDays
     */
    private static int holidaysBetweenPeriod(String checkoutDateString, int rentalDays, int yearsProgressed){
        Calendar checkoutDate = getCalendarFromString(checkoutDateString);
        log.info("Checkout date Calendar created: " + checkoutDate.getTime());

        int holidayCount = 0;

        Calendar returnDate = (Calendar) checkoutDate.clone();
        returnDate.add(Calendar.DAY_OF_YEAR, rentalDays);

        log.info("Created return date: " + returnDate.getTime());

        //find 4th of July occurrences between checkout date and return date
        Calendar julyFourth = new GregorianCalendar(checkoutDate.get(Calendar.YEAR), Calendar.JULY, 4);
        log.info("Created and set current year July 4th: " + julyFourth.getTime());
        //create labor day object and set to the first monday of sept
        Calendar laborDay = new GregorianCalendar(checkoutDate.get(Calendar.YEAR), Calendar.SEPTEMBER, 1);
        log.info("Created and set 'Labor Day' to first day of Sept: " + laborDay.getTime());
        setLaborDayDate(laborDay);


        for(int i = 0; i < yearsProgressed + 1; i++){
            //check to see if the checkout date and return date contain 4th of july
            if(checkoutDate.compareTo(julyFourth) < 0){
                if(returnDate.compareTo(julyFourth) > 0){
                    //adjust 4th of july date to appropriate weekday if it falls on a weekend
                    if(julyFourth.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
                        julyFourth.add(Calendar.DAY_OF_YEAR, -1);
                    }
                    else if(julyFourth.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                        julyFourth.add(Calendar.DAY_OF_YEAR, 1);
                    }
                    //increase the holiday
                    holidayCount++;
                    julyFourth.add(Calendar.YEAR, 1);
                }
            }
            log.info("July 4th year value incremented if found: " + julyFourth.getTime());
            if(checkoutDate.compareTo(laborDay) < 0) {
                if (returnDate.compareTo(laborDay) > 0) {
                    holidayCount++;
                }
            }
            laborDay.add(Calendar.YEAR, 1);
            log.info("Labor Day year value incremented  if found: " + julyFourth.getTime());
        }
        return holidayCount;
    }

    /**
     * Sets the inputted Calendar to the Labor Day date for it's year
     * @param laborDay - Calendar object to be set to Labor Day
     */
    private static void setLaborDayDate(Calendar laborDay){
        //set the calendar to the Monday of the current week
        laborDay.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        log.info("Set 'Labor Day' to Monday: " + laborDay.getTime());

        //if the Monday of the current week is in August, add 7 days to get to the first Monday of September
        if(laborDay.get(Calendar.MONTH) != Calendar.SEPTEMBER){
            laborDay.add(Calendar.DATE, 7);
            log.info("Ensure 'Labor Day' is in Sept: " + laborDay.getTime());
        }
    }

    /**
     * Calculates the number of weekend days given a start date and a number of days to progress
     * @param checkoutDateString - date to begin at
     * @param rentalDays - number of days to progress and check for weekends
     * @return - int representing the number of weekend days between the start date + rentalDays
     */
    public static int numberOfWeekendDays(String checkoutDateString, int rentalDays){
        Calendar checkoutDate = getCalendarFromString(checkoutDateString);

        int weekendDays = 0;
        int dayOfWeek = checkoutDate.get(Calendar.DAY_OF_WEEK);

        //check if current day is saturday
        if(dayOfWeek == 7){
            weekendDays--;
        }

        int daysRemaining = rentalDays%7;
        weekendDays += ((rentalDays/7) * 2);

        if(daysRemaining != 0){
            if((daysRemaining + dayOfWeek) > 6){
                weekendDays++;
                if((daysRemaining + dayOfWeek) > 7){
                    weekendDays++;
                }
            }
        }
        return weekendDays;
    }

    /**
     * Returns a date string that is the number of days after the dateString
     * @param dateString - start date
     * @param days - number of days to add to startDate
     * @return - String representing the date when adding the days input to the startDate
     */
    public static String getDateString(String dateString, int days){
        Calendar returnDate = getCalendarFromString(dateString);
        returnDate.add(Calendar.DAY_OF_YEAR, days);
        SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");

        return formatter.format(returnDate.getTime());
    }

    /**
     * Returns a Calendar object set to the date represented by the 'date' input
     * @param date - date to se the resulting Calendar object to
     * @return - Calendar set to the input date
     */
    private static Calendar getCalendarFromString(String date){
        Calendar calendar = new GregorianCalendar();
        String[] dateSplit = date.split("/");
        //Month indexed by 0 for some reason
        int month = Integer.parseInt(dateSplit[0]) - 1;
        int day = Integer.parseInt(dateSplit[1]);
        //add 2000 as all dates are the year 2000+
        int year = Integer.parseInt(dateSplit[2]) + 2000;
        calendar.set(year, month, day);

        return calendar;
    }
}
