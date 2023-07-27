package com.toolStore;

import com.google.gson.Gson;
import com.toolStore.model.RentalAgreementModel;
import com.toolStore.model.ToolModel;
import com.toolStore.model.ToolTypeModel;
import com.toolStore.model.Tools;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Checkout {

    private ArrayList<ToolModel> tools;
    private ArrayList<ToolTypeModel> toolTypes;
    private RentalAgreementModel rentalAgreementReport;

    public Checkout() {
        try {
            Gson gson = new Gson();
            Reader reader = Files.newBufferedReader(Paths.get("src/main/util/toolJson.json"));
            Tools tools = gson.fromJson(reader, Tools.class);
            this.tools = tools.getTools();
            this.toolTypes = tools.getToolTypes();

            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

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

    public RentalAgreementModel generateRentalAgreement(String toolCode, int rentalDayCount, int discount, String checkoutDate) throws Exception {
        ToolModel selectedToolInfo = findToolModel(toolCode);
        ToolTypeModel selectedToolTypeInfo = getToolTypeInfo(selectedToolInfo.getType());
        RentalAgreementModel rentalAgreement = new RentalAgreementModel(
                selectedToolInfo, selectedToolTypeInfo, rentalDayCount, checkoutDate, discount
        );
        return rentalAgreement;
    }


    private ToolModel findToolModel(String toolCode) {
        for (ToolModel tool : tools) {
            if (tool.getCode().equalsIgnoreCase(toolCode)) {
                return tool;
            }
        }
        return null;
    }

    private ToolTypeModel getToolTypeInfo(String selectedToolType) {
        for (ToolTypeModel toolType : toolTypes) {
            if (toolType.getToolType().equalsIgnoreCase(selectedToolType)) {
                return toolType;
            }
        }
        return null;
    }

    private boolean isValidToolCode(String toolCode) {
        for (ToolModel tool : tools) {
            if (tool.getCode().equalsIgnoreCase(toolCode)) {
                return true;
            }
        }
        return false;
    }

    public RentalAgreementModel getRentalAgreementReport() {
        return rentalAgreementReport;
    }
}


