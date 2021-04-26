package GenericStoreTests;

import GenericStore.RentalAgreement;
import GenericStore.Tool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
final class RentalAgreementTest {

    private Tool ladder;
    private Tool chainsaw;
    private Tool jackhammerR;
    private Tool jackhammerD;

    @BeforeAll
    void createObjects() throws Exception {
        ladder = new Tool("LADW");
        chainsaw = new Tool("CHNS");
        jackhammerR = new Tool("JAKR");
        jackhammerD = new Tool("JAKD");
    }

    /**
     * Test the amount of chargeable days using the doc scenarios 2-6
     */
    @Test
    void findChargeableDaysTest() throws Exception {
        RentalAgreement agr1 = new RentalAgreement(3, "7/2/20", ladder, 10);
        RentalAgreement agr2 = new RentalAgreement(5, "7/2/15", chainsaw, 25);
        RentalAgreement agr3 = new RentalAgreement(6, "9/3/15", jackhammerD, 0);
        RentalAgreement agr4 = new RentalAgreement(9, "7/2/15", jackhammerR, 0);
        RentalAgreement agr5 = new RentalAgreement(6, "7/2/20", jackhammerR, 50);

        assertEquals(2, agr1.getChargeableDays());
        assertEquals(3, agr2.getChargeableDays());
        assertEquals(3, agr3.getChargeableDays());
        assertEquals(5, agr4.getChargeableDays());
        assertEquals(3, agr5.getChargeableDays());
    }

    /**
     * Check if various values are as expected (doc scenario 2)
     */
    @Test
    void validAgreementParams_1() throws Exception{
        RentalAgreement agr1 = new RentalAgreement(3, "7/2/20", ladder, 10);
        int agr1ChargeDays = 2;
        double agr1DailyCharge = 1.99;
        //manually calculated values
        double agr1PreDiscountAmt = 3.98;
        double agr1DiscountAmt = .4;
        double agr1FinalCharge = 3.58;
        //check chargeable days
        assertEquals(agr1ChargeDays, agr1.getChargeableDays());
        //check to see if it gets the proper daily charge based off of the tool
        assertEquals(agr1DailyCharge, agr1.getDailyRentalCharge());
        //check to see if the pre-discount charge is correct (chargeableDays * dailyCharge)
        assertEquals(agr1PreDiscountAmt, agr1.getPreDiscountCharge());
        //check to see if the discount amount is correct (preDiscountAmount * (discountPercent/100))
        assertEquals(agr1DiscountAmt, agr1.getDiscountAmount());
        //check to see if the final amount is correct (preDiscountAmount - discountAmount)
        assertEquals(agr1FinalCharge, agr1.getFinalCharge());
    }

    /**
     * Check if various values are as expected (doc scenario 3)
     */
    @Test
    void validAgreementParams_2() throws Exception{
        RentalAgreement agr2 = new RentalAgreement(5, "7/2/15", chainsaw, 25);
        int agr2ChargeDays = 3;
        double agr2DailyCharge = 1.49;
        //manually calculated values
        double agr2PreDiscountAmt = 4.47;
        double agr2DiscountAmt = 1.12;
        double agr2FinalCharge = 3.35;
        //check chargeable days
        assertEquals(agr2ChargeDays, agr2.getChargeableDays());
        //check to see if it gets the proper daily charge based off of the tool
        assertEquals(agr2DailyCharge, agr2.getDailyRentalCharge());
        //check to see if the pre-discount charge is correct (chargeableDays * dailyCharge)
        assertEquals(agr2PreDiscountAmt, agr2.getPreDiscountCharge());
        //check to see if the discount amount is correct (preDiscountAmount * (discountPercent/100))
        assertEquals(agr2DiscountAmt, agr2.getDiscountAmount());
        //check to see if the final amount is correct (preDiscountAmount - discountAmount)
        assertEquals(agr2FinalCharge, agr2.getFinalCharge());
    }

    /**
     * Check if various values are as expected (doc scenario 4)
     */
    @Test
    void validAgreementParams_3() throws Exception{
        RentalAgreement agr3 = new RentalAgreement(6, "9/3/15", jackhammerD, 0);
        int agr3ChargeDays = 3;
        double agr3DailyCharge = 2.99;
        //manually calculated values
        double agr3PreDiscountAmt = 8.97;
        double agr3DiscountAmt = 0;
        double agr3FinalCharge = 8.97;
        //check chargeable days
        assertEquals(agr3ChargeDays, agr3.getChargeableDays());
        //check to see if it gets the proper daily charge based off of the tool
        assertEquals(agr3DailyCharge, agr3.getDailyRentalCharge());
        //check to see if the pre-discount charge is correct (chargeableDays * dailyCharge)
        assertEquals(agr3PreDiscountAmt, agr3.getPreDiscountCharge());
        //check to see if the discount amount is correct (preDiscountAmount * (discountPercent/100))
        assertEquals(agr3DiscountAmt, agr3.getDiscountAmount());
        //check to see if the final amount is correct (preDiscountAmount - discountAmount)
        assertEquals(agr3FinalCharge, agr3.getFinalCharge());
    }
    /**
     * Check if various values are as expected (doc scenario 5)
     */
    @Test
    void validAgreementParams_4() throws Exception{
        RentalAgreement agr4 = new RentalAgreement(9, "7/2/15", jackhammerR, 0);
        int agr4ChargeDays = 5;
        double agr4DailyCharge = 2.99;
        //manually calculated values
        double agr4PreDiscountAmt = 14.95;
        double agr4DiscountAmt = 0;
        double agr4FinalCharge = 14.95;
        //check chargeable days
        assertEquals(agr4ChargeDays, agr4.getChargeableDays());
        //check to see if it gets the proper daily charge based off of the tool
        assertEquals(agr4DailyCharge, agr4.getDailyRentalCharge());
        //check to see if the pre-discount charge is correct (chargeableDays * dailyCharge)
        assertEquals(agr4PreDiscountAmt, agr4.getPreDiscountCharge());
        //check to see if the discount amount is correct (preDiscountAmount * (discountPercent/100))
        assertEquals(agr4DiscountAmt, agr4.getDiscountAmount());
        //check to see if the final amount is correct (preDiscountAmount - discountAmount)
        assertEquals(agr4FinalCharge, agr4.getFinalCharge());
    }
    /**
     * Check if various values are as expected (doc scenario 6)
     */
    @Test
    void validAgreementParams_5() throws Exception{
        RentalAgreement agr5 = new RentalAgreement(6, "7/2/20", jackhammerR, 50);
        int agr5ChargeDays = 3;
        double agr5DailyCharge = 2.99;
        //manually calculated values
        double agr5PreDiscountAmt = 8.97;
        double agr5DiscountAmt = 4.49;
        double agr5FinalCharge = 4.48;
        //check chargeable days
        assertEquals(agr5ChargeDays, agr5.getChargeableDays());
        //check to see if it gets the proper daily charge based off of the tool
        assertEquals(agr5DailyCharge, agr5.getDailyRentalCharge());
        //check to see if the pre-discount charge is correct (chargeableDays * dailyCharge)
        assertEquals(agr5PreDiscountAmt, agr5.getPreDiscountCharge());
        //check to see if the discount amount is correct (preDiscountAmount * (discountPercent/100))
        assertEquals(agr5DiscountAmt, agr5.getDiscountAmount());
        //check to see if the final amount is correct (preDiscountAmount - discountAmount)
        assertEquals(agr5FinalCharge, agr5.getFinalCharge());
    }

    /**
     * Used to insure appropriate exception is thrown if 0 > discountPercent > 100
     * First instance uses doc scenario 1
     */
    @Test
    void invalidDiscountPercentage(){
        try{
            new RentalAgreement(5, "9/3/15", jackhammerR, 101);
            fail();
        }
        catch (Exception e){
            assertEquals("Discount percentage is out of the 0-100 range.", e.getMessage());
        }

        try{
            new RentalAgreement(5, "9/3/15", jackhammerR, -1);
            fail();
        }
        catch (Exception e){
            assertEquals("Discount percentage is out of the 0-100 range.", e.getMessage());
        }
    }

    @Test
    void invalidRentalDay(){
        try{
            new RentalAgreement(0, "9/3/15", jackhammerR, 0);
            fail();
        }
        catch (Exception e){
            assertEquals("Rental days must be greater than 0.", e.getMessage());
        }

        try{
            new RentalAgreement(-1, "9/3/15", jackhammerR, 0);
            fail();
        }
        catch (Exception e){
            assertEquals("Rental days must be greater than 0.", e.getMessage());
        }
    }


}