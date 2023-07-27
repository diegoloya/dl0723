package com.toolStore;

import com.toolStore.model.RentalAgreement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckoutTest {

    @Test
    void shouldThrowExceptionWhenDiscountIsMoreThan100_test1() {
        String toolCode = "JAKR";
        String checkoutDate = "9/3/15";
        int rentalDays = 5;
        int discount = 101;
        Checkout checkout = new Checkout();
        Assertions.assertThrows(Exception.class, () -> checkout.generateRentalAgreement(toolCode, rentalDays, discount, checkoutDate));
    }

    @Test
    void shouldCreateReportWithCorrectValues_test2() throws Exception {

        String toolCode = "LADW";
        String checkoutDate = "7/2/20";
        int rentalDays = 3;
        int discount = 10;
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.generateRentalAgreement(toolCode, rentalDays, discount, checkoutDate);

        Assertions.assertEquals(toolCode, agreement.getToolCode());
        Assertions.assertEquals("Ladder", agreement.getToolType());
        Assertions.assertEquals("Werner", agreement.getToolBrand());
        Assertions.assertEquals(rentalDays, agreement.getRentalDays());
        Assertions.assertEquals("07/02/20", agreement.getCheckoutDate());
        Assertions.assertEquals("07/05/20", agreement.getDueDate());
        Assertions.assertEquals(BigDecimal.valueOf(1.99), agreement.getDailyRentalCharge());
        Assertions.assertEquals(2, agreement.getChargeDays());
        Assertions.assertEquals(BigDecimal.valueOf(3.98).setScale(2, RoundingMode.HALF_UP), agreement.getPrediscountCharge());
        Assertions.assertEquals(BigDecimal.valueOf(0.40).setScale(2, RoundingMode.HALF_UP), agreement.getDiscountAmount());
        Assertions.assertEquals(BigDecimal.valueOf(3.58).setScale(2, RoundingMode.HALF_UP), agreement.getFinalCharge());

    }

    @Test
    void shouldCreateReportWithCorrectValues_test3() throws Exception {

        String toolCode = "CHNS";
        String checkoutDate = "7/2/15";
        int rentalDays = 5;
        int discount = 25;
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.generateRentalAgreement(toolCode, rentalDays, discount, checkoutDate);

        Assertions.assertEquals(toolCode, agreement.getToolCode());
        Assertions.assertEquals("Chainsaw", agreement.getToolType());
        Assertions.assertEquals("Stihl", agreement.getToolBrand());
        Assertions.assertEquals(rentalDays, agreement.getRentalDays());
        Assertions.assertEquals("07/02/15", agreement.getCheckoutDate());
        Assertions.assertEquals("07/07/15", agreement.getDueDate());
        Assertions.assertEquals(BigDecimal.valueOf(1.49), agreement.getDailyRentalCharge());
        Assertions.assertEquals(3, agreement.getChargeDays());
        Assertions.assertEquals(BigDecimal.valueOf(4.47).setScale(2, RoundingMode.HALF_UP), agreement.getPrediscountCharge());
        Assertions.assertEquals(BigDecimal.valueOf(1.34).setScale(2, RoundingMode.HALF_UP), agreement.getDiscountAmount());
        Assertions.assertEquals(BigDecimal.valueOf(3.13).setScale(2, RoundingMode.HALF_UP), agreement.getFinalCharge());

    }

    @Test
    void shouldCreateReportWithCorrectValues_test4() throws Exception {

        String toolCode = "JAKD";
        String checkoutDate = "9/3/15";
        int rentalDays = 6;
        int discount = 0;
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.generateRentalAgreement(toolCode, rentalDays, discount, checkoutDate);

        Assertions.assertEquals(toolCode, agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("DeWalt", agreement.getToolBrand());
        Assertions.assertEquals(rentalDays, agreement.getRentalDays());
        Assertions.assertEquals("09/03/15", agreement.getCheckoutDate());
        Assertions.assertEquals("09/09/15", agreement.getDueDate());
        Assertions.assertEquals(BigDecimal.valueOf(2.99), agreement.getDailyRentalCharge());
        Assertions.assertEquals(3, agreement.getChargeDays());
        Assertions.assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), agreement.getPrediscountCharge());
        Assertions.assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), agreement.getDiscountAmount());
        Assertions.assertEquals(BigDecimal.valueOf(8.97).setScale(2, RoundingMode.HALF_UP), agreement.getFinalCharge());

    }

    @Test
    void shouldCreateReportWithCorrectValues_test5() throws Exception {

        String toolCode = "JAKR";
        String checkoutDate = "7/2/15";
        int rentalDays = 9;
        int discount = 0;
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.generateRentalAgreement(toolCode, rentalDays, discount, checkoutDate);

        Assertions.assertEquals(toolCode, agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("Ridgid", agreement.getToolBrand());
        Assertions.assertEquals(rentalDays, agreement.getRentalDays());
        Assertions.assertEquals("07/02/15", agreement.getCheckoutDate());
        Assertions.assertEquals("07/11/15", agreement.getDueDate());
        Assertions.assertEquals(BigDecimal.valueOf(2.99), agreement.getDailyRentalCharge());
        Assertions.assertEquals(6, agreement.getChargeDays());
        Assertions.assertEquals(BigDecimal.valueOf(17.94).setScale(2, RoundingMode.HALF_UP), agreement.getPrediscountCharge());
        Assertions.assertEquals(BigDecimal.valueOf(0.00).setScale(2, RoundingMode.HALF_UP), agreement.getDiscountAmount());
        Assertions.assertEquals(BigDecimal.valueOf(17.94).setScale(2, RoundingMode.HALF_UP), agreement.getFinalCharge());

    }

    @Test
    void shouldCreateReportWithCorrectValues_test6() throws Exception {

        String toolCode = "JAKR";
        String checkoutDate = "7/2/20";
        int rentalDays = 4;
        int discount = 50;
        Checkout checkout = new Checkout();
        RentalAgreement agreement = checkout.generateRentalAgreement(toolCode, rentalDays, discount, checkoutDate);

        Assertions.assertEquals(toolCode, agreement.getToolCode());
        Assertions.assertEquals("Jackhammer", agreement.getToolType());
        Assertions.assertEquals("Ridgid", agreement.getToolBrand());
        Assertions.assertEquals(rentalDays, agreement.getRentalDays());
        Assertions.assertEquals("07/02/20", agreement.getCheckoutDate());
        Assertions.assertEquals("07/06/20", agreement.getDueDate());
        Assertions.assertEquals(BigDecimal.valueOf(2.99), agreement.getDailyRentalCharge());
        Assertions.assertEquals(1, agreement.getChargeDays());
        Assertions.assertEquals(BigDecimal.valueOf(2.99).setScale(2, RoundingMode.HALF_UP), agreement.getPrediscountCharge());
        Assertions.assertEquals(BigDecimal.valueOf(1.50).setScale(2, RoundingMode.HALF_UP), agreement.getDiscountAmount());
        Assertions.assertEquals(BigDecimal.valueOf(1.49).setScale(2, RoundingMode.HALF_UP), agreement.getFinalCharge());

    }

    @Test
    void shouldThrowExceptionWhenRentalDaysIsLessThan1() {
        String toolCode = "JAKR";
        String checkoutDate = "9/3/15";
        int rentalDays = 0;
        int discount = 10;
        Checkout checkout = new Checkout();
        Assertions.assertThrows(Exception.class, () -> checkout.generateRentalAgreement(toolCode, rentalDays, discount, checkoutDate));
    }

}
