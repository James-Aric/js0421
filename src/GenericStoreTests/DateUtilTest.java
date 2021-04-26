package GenericStoreTests;

import GenericStore.DateUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

final class DateUtilTest {


    /**
     * Test various start dates and periods to check how many weekdays are contained (post holiday removal)
     * (Manually checked using https://www.timeanddate.com/date/workdays.html)
     */
    @Test
    void weekdaysBetweenPeriod() {
        String date1 = "4/1/21";
        String date2 = "7/6/24";
        String date3 = "2/1/10";

        int rentalDays1 = 10;
        int rentalDays2 = 15;
        int rentalDays3 = 3;

        int periodTestA1 = DateUtil.weekdaysBetweenPeriod(date1, rentalDays1);
        int periodTestA2 = DateUtil.weekdaysBetweenPeriod(date1, rentalDays2);
        int periodTestA3 = DateUtil.weekdaysBetweenPeriod(date1, rentalDays3);

        assertEquals(6, periodTestA1);
        assertEquals(11, periodTestA2);
        assertEquals(1, periodTestA3);

        int periodTestB1 = DateUtil.weekdaysBetweenPeriod(date2, rentalDays1);
        int periodTestB2 = DateUtil.weekdaysBetweenPeriod(date2, rentalDays2);
        int periodTestB3 = DateUtil.weekdaysBetweenPeriod(date2, rentalDays3);

        assertEquals(7, periodTestB1);
        assertEquals(10, periodTestB2);
        assertEquals(2, periodTestB3);

        int periodTestC1 = DateUtil.weekdaysBetweenPeriod(date3, rentalDays1);
        int periodTestC2 = DateUtil.weekdaysBetweenPeriod(date3, rentalDays2);
        int periodTestC3 = DateUtil.weekdaysBetweenPeriod(date3, rentalDays3);

        assertEquals(8, periodTestC1);
        assertEquals(11, periodTestC2);
        assertEquals(3, periodTestC3);
    }

    /**
     * Test various start dates and periods to test if there are holidays contained
     */
    @Test
    void containsHolidays() {
        String date1 = "4/1/21";
        String date2 = "7/2/24";
        String date3 = "9/1/10";

        int rentalDays1 = 10;
        int rentalDays2 = 15;
        int rentalDays3 = 100;

        int holidayTestA1 = DateUtil.containsHolidays(date1, rentalDays1);
        assertEquals(0, holidayTestA1);
        int holidayTestA2 = DateUtil.containsHolidays(date1, rentalDays2);
        assertEquals(0, holidayTestA2);
        //contains 1 holiday (7/4/21)
        int holidayTestA3 = DateUtil.containsHolidays(date1, rentalDays3);
        assertEquals(1, holidayTestA3);

        //contains 1 holiday (7/4/24)
        int holidayTestB1 = DateUtil.containsHolidays(date2, rentalDays1);
        assertEquals(1, holidayTestB1);
        //contains 1 holiday (7/4/24)
        int holidayTestB2 = DateUtil.containsHolidays(date2, rentalDays2);
        assertEquals(1, holidayTestB2);
        //contians 2 holidays (7/4/24, labor day)
        int holidayTestB3 = DateUtil.containsHolidays(date2, rentalDays3);
        assertEquals(2, holidayTestB3);

        //all contain 1 holiday (labor day)
        int holidayTestC1 = DateUtil.containsHolidays(date3, rentalDays1);
        assertEquals(1, holidayTestC1);
        int holidayTestC2 = DateUtil.containsHolidays(date3, rentalDays2);
        assertEquals(1, holidayTestC2);
        int holidayTestC3 = DateUtil.containsHolidays(date3, rentalDays3);
        assertEquals(1, holidayTestC3);
    }

    /**
     * Test various start dates and periods to check how many weekend days are contained
     * (Manually checked using https://www.timeanddate.com/date/workdays.html)
     */
    @Test
    void numberOfWeekendDays() {
        String date1 = "4/1/21";
        String date2 = "7/2/24";
        String date3 = "9/1/10";

        int rentalDays1 = 10;
        int rentalDays2 = 15;
        int rentalDays3 = 100;

        int holidayTestA1 = DateUtil.numberOfWeekendDays(date1, rentalDays1);
        assertEquals(4, holidayTestA1);
        int holidayTestA2 = DateUtil.numberOfWeekendDays(date1, rentalDays2);
        assertEquals(4, holidayTestA2);
        int holidayTestA3 = DateUtil.numberOfWeekendDays(date1, rentalDays3);
        assertEquals(29, holidayTestA3);

        int holidayTestB1 = DateUtil.numberOfWeekendDays(date2, rentalDays1);
        assertEquals(2, holidayTestB1);
        int holidayTestB2 = DateUtil.numberOfWeekendDays(date2, rentalDays2);
        assertEquals(4, holidayTestB2);
        int holidayTestB3 = DateUtil.numberOfWeekendDays(date2, rentalDays3);
        assertEquals(28, holidayTestB3);

        int holidayTestC1 = DateUtil.numberOfWeekendDays(date3, rentalDays1);
        assertEquals(3, holidayTestC1);
        int holidayTestC2 = DateUtil.numberOfWeekendDays(date3, rentalDays2);
        assertEquals(4, holidayTestC2);
        int holidayTestC3 = DateUtil.numberOfWeekendDays(date3, rentalDays3);
        assertEquals(28, holidayTestC3);
    }
}