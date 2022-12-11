package org.currencyexchangepp;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import static org.currencyexchangepp.CurrencyConverterService.*;

public class Main {
    public static void main(String[] args) throws Exception {
        List<String> symbols = new LinkedList<>();
        Scanner cin = new Scanner(System.in);

        System.out.println("Enter currency base type FROM:");
        String from = cin.next();
        System.out.println("Currency from: " + from.toUpperCase());

        System.out.println("Enter currency base type TO:");
        String to = cin.next();
        System.out.println("Currency to: " + to.toUpperCase());

        System.out.println("Enter amount:");
        int amount = cin.nextInt();
        System.out.println("Amount: " + amount);

        while (amount <= 0) {
            System.out.println("Amount must be > 0. Please re-enter");
            amount = cin.nextInt();
        }

        String response =
                CurrencyConverterService
                        .makeApplicationRestrictedRequest(
                                getExchangesEndPoints(from, to, String.valueOf(amount)),
                                getAPIKey()
                        );

        System.out.println("convert from " + from + " to " + to + " : " +response);
//
//        System.out.println("Enter currency base:");
//        String base = cin.next().toUpperCase().trim();
//        System.out.println("Currency from: " + base);
//
//        System.out.println("Enter number of Symbols: ");
//        int size = cin.nextInt();
//
//        while (size <= 0) {
//            System.out.println("Must be > 0. Please re-enter");
//            size = cin.nextInt();
//        }
//
//        for(int i = 0; i < size; i++) {
//            System.out.println("Enter symbols:");
//            String symbol = cin.next().toUpperCase().trim();
//            symbols.add(symbol);
//            System.out.println("Symbol " + i + ": " + symbol);
//        }
//
//        String responseLastestAPI =
//                CurrencyConverterService
//                        .makeApplicationRestrictedRequest(getLastestEndPoints(base, symbols), getAPIKey());
//        System.out.println("Lastest rate: " + responseLastestAPI);
//        System.out.println();
        saveJsonToFile("rateConverter.txt", response);
        openFile("rateConverter.txt");
    }
}
