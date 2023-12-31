package com.toolStore.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class RentalAgreement {
    private final String toolCode;
    private final String toolType;
    private final String toolBrand;
    private int rentalDays;
    private String checkoutDate;
    private final String dueDate;
    private final BigDecimal dailyRentalCharge;
    private final int chargeDays;
    private final BigDecimal prediscountCharge;
    private int discountPercent;
    private final BigDecimal discountAmount;
    private final BigDecimal finalCharge;

    //Constructor will orchestrate rental agreement creation using given values from user and inventory
    public RentalAgreement(Tool selectedToolInfo, ToolType selectedToolTypeInfo, int rentalDays, String checkoutDate, int discountPercent) throws Exception {
        setDiscountPercent(discountPercent);
        setRentalDays(rentalDays);
        setCheckoutDate(checkoutDate);
        this.toolCode = selectedToolInfo.getCode();
        this.toolType = selectedToolInfo.getType();
        this.toolBrand = selectedToolInfo.getBrand();
        this.dueDate = this.getDueDate(checkoutDate, rentalDays);
        this.dailyRentalCharge = selectedToolTypeInfo.getDailyCharge();
        this.chargeDays = this.getChargeDays(checkoutDate, rentalDays, selectedToolTypeInfo);
        this.prediscountCharge = this.getPrediscountCharge(chargeDays, selectedToolTypeInfo.getDailyCharge());
        this.discountAmount = this.getDiscountAmount(prediscountCharge, discountPercent);
        this.finalCharge = this.getFinalCharge(prediscountCharge, discountAmount);

    }


    //simple calculations based on initial checkout date plus rental day count
    private String getDueDate(String checkoutDate, int rentalDayCount) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uu");
        LocalDate localCheckoutDate = validateDate(checkoutDate);

        return localCheckoutDate.plusDays(rentalDayCount).format(formatter);
    }

    //multiplies charge days by daily charge
    private BigDecimal getPrediscountCharge(int chargeDays, BigDecimal dailyCharge) {
        return BigDecimal.valueOf(chargeDays).multiply(dailyCharge);
    }

    //calculates discount by diving given percentage by 100 to get decimal value and multiplying that by the prediscount charge
    private BigDecimal getDiscountAmount(BigDecimal preDiscountCharge, int discount) {
        BigDecimal decimalDiscount = BigDecimal.valueOf(discount).divide(BigDecimal.valueOf(100), 1, RoundingMode.HALF_UP);
        return preDiscountCharge.multiply(decimalDiscount).setScale(2, RoundingMode.HALF_UP);
    }

    //prediscount charge - discount
    private BigDecimal getFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount) {
        return preDiscountCharge.subtract(discountAmount);
    }

    //calculates charge days based on information given on InventoryInfo.json regarding weekday/weekend/holiday charges
    private int getChargeDays(String checkoutDate, int rentalDayCount, ToolType toolTypeInfo) {
        int chargeDays = 0;
        LocalDate localCheckoutDate = validateDate(checkoutDate);


        List<LocalDate> holidays = getHolidays(localCheckoutDate, rentalDayCount);
        int index = 0;

        while (index < rentalDayCount) {
            boolean isWeekend = (localCheckoutDate.plusDays(index).getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                    localCheckoutDate.plusDays(index).getDayOfWeek().equals(DayOfWeek.SUNDAY));
            boolean isHoliday = holidays.contains(localCheckoutDate.plusDays(index));

            if ((isWeekend && toolTypeInfo.isWeekendCharge()) ||
                    (isHoliday && toolTypeInfo.isHolidayCharge()) ||
                    (!isHoliday && !isWeekend && toolTypeInfo.isWeekdayCharge())
            ) {
                chargeDays++;
            }
            index++;
        }
        return chargeDays;
    }

    //returns a list of holiday dates based on the criteria given for weekend 4th of july and labor day mondays
    private ArrayList<LocalDate> getHolidays(LocalDate checkoutDate, int rentalDayCount) {

        ArrayList<LocalDate> holidayList = new ArrayList<>();
        int years = rentalDayCount / 365 + 1;

        for (int i = 0; i < years; i++) {
            holidayList.add(getActualIndependenceDay(checkoutDate.getYear() + i));
            holidayList.add(getActualLaborDay(checkoutDate.getYear() + i));
        }
        return holidayList;
    }


    //checks if 4th of july is on weekend and adds or subtracts one day
    private LocalDate getActualIndependenceDay(int year) {
        return switch (LocalDate.of(year, 7, 4).getDayOfWeek()) {
            case SATURDAY -> LocalDate.of(year, 7, 3);
            case SUNDAY -> LocalDate.of(year, 7, 5);
            default -> LocalDate.of(year, 7, 4);
        };
    }

    //calculates 1st monday of the month relative to day of the week of the 1st day of the month
    private LocalDate getActualLaborDay(int year) {
        return switch (LocalDate.of(year, 9, 1).getDayOfWeek()) {
            case MONDAY -> LocalDate.of(year, 9, 1);
            case SUNDAY -> LocalDate.of(year, 9, 2);
            case SATURDAY -> LocalDate.of(year, 9, 3);
            case FRIDAY -> LocalDate.of(year, 9, 4);
            case THURSDAY -> LocalDate.of(year, 9, 5);
            case WEDNESDAY -> LocalDate.of(year, 9, 6);
            case TUESDAY -> LocalDate.of(year, 9, 7);
        };

    }


    //formats and prints user-friendly report
    public void printReport() {
        System.out.println("Tool code:............." + this.toolCode);
        System.out.println("Tool type:............." + this.toolType);
        System.out.println("Tool brand:............" + this.toolBrand);
        System.out.println("Rental days:..........." + this.rentalDays);
        System.out.println("Check out date:........" + this.getCheckoutDate());
        System.out.println("Due date:.............." + this.dueDate);
        System.out.println("Daily rental charge:...$" + String.format("%,.2f", this.dailyRentalCharge));
        System.out.println("Charge days:..........." + this.chargeDays);
        System.out.println("Pre-discount charge:...$" + String.format("%,.2f", this.prediscountCharge));
        System.out.println("Discount percent:......%" + this.discountPercent);
        System.out.println("Discount amount:.......$" + String.format("%,.2f", this.discountAmount));
        System.out.println("Final charge:..........$" + String.format("%,.2f", this.finalCharge));
    }

    //validates user given date to accept different formats in effort to give user more flexibility on input
    private LocalDate validateDate(String date) {
        LocalDate localDate = null;
        List<DateTimeFormatter> formatterList = new ArrayList<>();
        formatterList.add(DateTimeFormatter.ofPattern("MM/dd/uu"));
        formatterList.add(DateTimeFormatter.ofPattern("MM/d/uu"));
        formatterList.add(DateTimeFormatter.ofPattern("MM/d/uuuu"));
        formatterList.add(DateTimeFormatter.ofPattern("MM/dd/uuuu"));
        formatterList.add(DateTimeFormatter.ofPattern("M/d/uu"));
        formatterList.add(DateTimeFormatter.ofPattern("M/d/uuuu"));
        formatterList.add(DateTimeFormatter.ofPattern("M/dd/uu"));
        formatterList.add(DateTimeFormatter.ofPattern("M/dd/uuuu"));

        while (localDate == null) {
            for (DateTimeFormatter formatter : formatterList) {
                try {
                    localDate = LocalDate.parse(date, formatter);
                } catch (DateTimeParseException pe) {
                } catch (Exception ex) {
                    return null;
                }
            }
        }
        return localDate;
    }

    public String getToolCode() {
        return toolCode;
    }

    public String getToolType() {
        return toolType;
    }

    public String getToolBrand() {
        return toolBrand;
    }

    public int getRentalDays() {
        return rentalDays;
    }


    //sets rental days and throws exception based on given criteria
    private void setRentalDays(int rentalDays) throws Exception {
        if (rentalDays <= 0) {
            throw new Exception("Rental days must be at least 1. Please try again.");
        }
        this.rentalDays = rentalDays;
    }

    public String getCheckoutDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/uu");
        LocalDate localCheckoutDate = validateDate(checkoutDate);
        return localCheckoutDate.format(formatter);
    }

    //sets checkout date and throws exception based on given criteria
    private void setCheckoutDate(String checkoutDate) throws Exception {
        if (validateDate(checkoutDate) == null) {
            throw new Exception("Date is invalid. Please use this format: mm/dd/yy");
        }
        this.checkoutDate = checkoutDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public BigDecimal getDailyRentalCharge() {
        return dailyRentalCharge;
    }

    public int getChargeDays() {
        return chargeDays;
    }

    public BigDecimal getPrediscountCharge() {
        return prediscountCharge;
    }

    public int getDiscountPercent() throws Exception {
        return discountPercent;
    }

    //sets discount and throws exception based on given criteria
    private void setDiscountPercent(int discountPercent) throws Exception {
        if (discountPercent < 0 || discountPercent > 100) {
            throw new Exception("Discount should be between 0 and 100. Please try again.");
        }
        this.discountPercent = discountPercent;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public BigDecimal getFinalCharge() {
        return finalCharge;
    }
}
