package com.toolStore;

import com.toolStore.model.RentalAgreementModel;

import java.util.Scanner;

public class ToolStoreApplication {

    public static void main(String[] args) {

        boolean isRunNewReport = true;
        Scanner scanner = new Scanner(System.in);
        while (isRunNewReport) {
            Checkout checkout = new Checkout();
            try {
                scanner.reset();
                checkout.userInteraction(scanner);
                RentalAgreementModel report = checkout.getRentalAgreementReport();
                System.out.println();
                report.printReport();
                System.out.println();
                System.out.println("Run new report? Y or N");
                String response = scanner.next();
                if (response.equalsIgnoreCase("y")) {
                    isRunNewReport = true;
                } else if (response.equalsIgnoreCase("n")) {
                    isRunNewReport = false;
                } else {
                    System.out.println("Invalid response, thank you!");
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("Bye!");
        scanner.close();


    }
}