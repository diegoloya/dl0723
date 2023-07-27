package com.toolStore;

import com.google.gson.Gson;
import com.toolStore.model.RentalAgreement;
import com.toolStore.model.Tool;
import com.toolStore.model.ToolType;
import com.toolStore.model.ToolList;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Checkout {

    private ArrayList<Tool> tools;
    private ArrayList<ToolType> toolTypes;
    private RentalAgreement rentalAgreementReport;

    //Constructor obtains data from InventoryInfo.json file. This allows for simple additions to inventory.
    public Checkout() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src/main/util/InventoryInfo.json"));
            ToolList toolList = gson.fromJson(reader, ToolList.class);
            this.tools = toolList.getTools();
            this.toolTypes = toolList.getToolTypes();

            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //Requests user input and does simple validation on input. Further validation will be done in RentalAgreement.java
    public void userInteraction(Scanner scanner) throws Exception {
        String toolCode;
        int rentalDayCount;
        double rentalDayCountInput;
        int discount;
        double discountInput;
        String checkoutDate;

        System.out.println("Enter a tool code: ");
        toolCode = scanner.next();
        while (!isValidToolCode(toolCode)) {
            System.out.println("Invalid tool code. Please enter a different tool code:");
            toolCode = scanner.next();
        }

        System.out.println("Enter a rental day count: ");
        rentalDayCountInput = scanner.nextDouble();
        while (rentalDayCountInput > 21652) {
            System.out.println("Invalid rental day count. Please enter a value between 1 and 21652:");
            rentalDayCountInput = scanner.nextDouble();
        }
        rentalDayCount = (int) rentalDayCountInput;

        System.out.println("Enter a discount percent: ");
        discountInput = scanner.nextDouble();
        while (discountInput > 2147483647) {
            System.out.println("Invalid discount. Please enter a value between 0 and 100:");
            discountInput = scanner.nextDouble();
        }
        discount = (int) discountInput;


        System.out.println("Enter a checkout date: ");
        checkoutDate = scanner.next();

        this.rentalAgreementReport = generateRentalAgreement(toolCode, rentalDayCount, discount, checkoutDate);
    }

    //Generates and returns RentalAgreement instance with values
    public RentalAgreement generateRentalAgreement(String toolCode, int rentalDayCount, int discount, String checkoutDate) throws Exception {
        Tool selectedToolInfo = findToolModel(toolCode);
        ToolType selectedToolTypeInfo = getToolTypeInfo(selectedToolInfo.getType());
        RentalAgreement rentalAgreement = new RentalAgreement(
                selectedToolInfo, selectedToolTypeInfo, rentalDayCount, checkoutDate, discount
        );
        return rentalAgreement;
    }


    private Tool findToolModel(String toolCode) {
        for (Tool tool : tools) {
            if (tool.getCode().equalsIgnoreCase(toolCode)) {
                return tool;
            }
        }
        return null;
    }

    private ToolType getToolTypeInfo(String selectedToolType) {
        for (ToolType toolType : toolTypes) {
            if (toolType.getToolType().equalsIgnoreCase(selectedToolType)) {
                return toolType;
            }
        }
        return null;
    }

    private boolean isValidToolCode(String toolCode) {
        for (Tool tool : tools) {
            if (tool.getCode().equalsIgnoreCase(toolCode)) {
                return true;
            }
        }
        return false;
    }

    public RentalAgreement getRentalAgreementReport() {
        return rentalAgreementReport;
    }
}


