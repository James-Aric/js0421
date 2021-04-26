package GenericStore;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Calculates all rental agreement fields based on the input provided
 */
public class RentalAgreement {

    //Number of days the tool is being rented
    private final int rentalDays;
    private final String checkoutDate;
    private final String returnDate;

    //The number of days within the rental period that are actually chargable
    private final int chargeableDays;

    private final Tool tool;
    private final String toolCode;
    private final String toolType;
    private final String toolBrand;

    private final double dailyRentalCharge;
    private final int discountPercentage;
    private final double preDiscountCharge;
    private final double discountAmount;
    private final double finalCharge;

    public RentalAgreement(int rentalDays, String checkoutDate, Tool tool, int discountPercentage) throws Exception {
        if (rentalDays < 1) {
            throw new Exception("Rental days must be greater than 0.");
        }
        this.rentalDays = rentalDays;
        this.checkoutDate = checkoutDate;
        this.returnDate = DateUtil.getDateString(checkoutDate, rentalDays);
        this.tool = tool;
        this.chargeableDays = this.findChargeableDays(this.checkoutDate, this.rentalDays, this.tool);
        this.toolCode = this.tool.getToolCode();
        this.toolType = this.tool.getType();
        this.toolBrand = this.tool.getBrand();
        this.dailyRentalCharge = getCostPerDay(tool);
        this.preDiscountCharge = getPreDiscountCharge(this.chargeableDays, this.dailyRentalCharge);
        if (discountPercentage > 100 || discountPercentage < 0) {
            throw new Exception("Discount percentage is out of the 0-100 range.");
        }
        this.discountPercentage = discountPercentage;
        this.discountAmount = getDiscountAmount(this.preDiscountCharge, this.discountPercentage);
        this.finalCharge = calculateFinalCharge(this.preDiscountCharge, this.discountAmount);
    }


    /**
     * Returns the cost per day based on the Tool object passed in
     *
     * @param tool - this classes tool object
     * @return - double representing cost per day based on Tool.getType()
     */
    private double getCostPerDay(Tool tool) {
        switch (tool.getType()) {
            case "Ladder":
                return 1.99;

            case "Chainsaw":
                return 1.49;

            case "Jackhammer":
                return 2.99;
            default:
                return -1;
        }
    }

    /**
     * Calculates the charge before the discount
     *
     * @param chargeableDays - number of days that are chargeable
     * @param costPerDay     - cost per day based on the tool type
     * @return - rounded double representing pre-discount charge amount
     */
    private double getPreDiscountCharge(int chargeableDays, double costPerDay) {
        double localDiscountPrice = costPerDay * chargeableDays;
        //round to half up to the nearest cent
        BigDecimal roundedCharge = BigDecimal.valueOf(localDiscountPrice);
        roundedCharge = roundedCharge.setScale(2, BigDecimal.ROUND_HALF_UP);

        return roundedCharge.doubleValue();
    }

    /**
     * Calculated the discount amount
     *
     * @param preDiscountCharge  - charge before the discount
     * @param discountPercentage - discount percentage as an int which is > 0 and < 100
     * @return - rounded double representing the amount of the discount
     */
    private double getDiscountAmount(double preDiscountCharge, int discountPercentage) {
        double discountAsPercentage = (double) discountPercentage / 100;
        double localDiscountAmount = preDiscountCharge * discountAsPercentage;
        //round to half up to the nearest cent
        BigDecimal roundedCharge = BigDecimal.valueOf(localDiscountAmount);
        roundedCharge = roundedCharge.setScale(2, BigDecimal.ROUND_HALF_UP);

        return roundedCharge.doubleValue();
    }

    /**
     * Calculates the final charge based on the discount amount and pre-discount charge
     *
     * @param preDiscountCharge - charge before the discount
     * @param discountAmount    - amount of the discount
     * @return - rounded double representing the final charge amount
     */
    private double calculateFinalCharge(double preDiscountCharge, double discountAmount) {
        double localFinalCharge = preDiscountCharge - discountAmount;
        //round to half up to the nearest cent (not necessarily needed here, but java does odd things with doubles)
        BigDecimal roundedCharge = BigDecimal.valueOf(localFinalCharge);
        roundedCharge = roundedCharge.setScale(2, BigDecimal.ROUND_HALF_UP);

        return roundedCharge.doubleValue();
    }

    /**
     * Uses the DateUtil class to calculate the number of chargeable days based on what the inputted tool has charges for
     *
     * @param checkoutDate - the date String representing the checkout date
     * @param rentalDays - the amount of days the tool is being rented
     * @param tool - the tool being rented
     * @return - int representing the amount of days that can be charged
     */
    private int findChargeableDays(String checkoutDate, int rentalDays, Tool tool) {
        int daySum;
        int weekdays = DateUtil.weekdaysBetweenPeriod(checkoutDate, rentalDays);
        int holidays = 0;
        int weekendDays = 0;

        //if the tool charges on holidays
        if (tool.holidayCharge()) {
            holidays += DateUtil.containsHolidays(checkoutDate, rentalDays);
        }
        //if the tool charges on weekends
        if (tool.weekendCharge()) {
            weekendDays += DateUtil.numberOfWeekendDays(checkoutDate, rentalDays);
        }

        daySum = weekdays + holidays + weekendDays;

        return daySum;
    }

    /**
     * Builds a String containing all the info that needs to be displayed to the user
     * @return - String representation of the RentalAgreement
     */
    public String getRentalAgreementOutput() {
        DecimalFormat formatter = new DecimalFormat("#,###.##");
        StringBuilder builder = new StringBuilder();

        builder.append("Tool code: ");
        builder.append(this.toolCode);
        builder.append("\nTool type: ");
        builder.append(this.toolType);

        builder.append("\nTool brand: ");
        builder.append(this.toolBrand);
        builder.append("\nRental days: ");
        builder.append(this.rentalDays);
        builder.append("\nCheckout date: ");
        builder.append(this.checkoutDate);
        builder.append("\nDue date: ");
        builder.append(this.returnDate);

        builder.append("\nDaily rental charge: $");
        builder.append(formatter.format(this.dailyRentalCharge));
        builder.append("\nCharge days: ");
        builder.append(this.chargeableDays);
        builder.append("\nPre-discount charge: $");
        builder.append(formatter.format(this.preDiscountCharge));
        builder.append("\nDiscount percent: ");
        builder.append(this.discountPercentage);
        builder.append("%");

        builder.append("\nDiscount amount: $");
        builder.append(formatter.format(this.discountAmount));
        builder.append("\nFinal charge: $");
        builder.append(formatter.format(this.finalCharge));

        return builder.toString();
    }

    //Return methods mostly for unit test purposes
    public double getDailyRentalCharge() {
        return this.dailyRentalCharge;
    }

    public int getChargeableDays() {
        return this.chargeableDays;
    }

    public double getPreDiscountCharge() {
        return this.preDiscountCharge;
    }

    public double getDiscountAmount() {
        return this.discountAmount;
    }

    public double getFinalCharge() {
        return this.finalCharge;
    }

}
