package org.currencyexchangepp;

import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
public class CurrencyConverterService {
    public static String makeApplicationRestrictedRequest(String endpoint, String APIKey) throws Exception {
        // Setup connection
        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("apikey", APIKey);

        if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
            InputStream inputStream =  connection.getErrorStream();
            String streamText = new String(inputStream.readAllBytes());
            throw new Exception(streamText);
        }

        // Return response
        InputStream inputStream = connection.getInputStream();
        return new String(inputStream.readAllBytes());
    }

    public static String getExchangesEndPoints(String from, String to, String amount){
        return "https://api.apilayer.com/exchangerates_data/convert?to=" + to + "&from=" + from + "&amount=" + amount;
    }

    public static String getLastestEndPoints(String base, List<String> symbols){
        return "https://api.apilayer.com/exchangerates_data/latest?symbols=" + retrieveDataFromList(symbols) + "&base=" + base;
    }

    public static String getAPIKey(){
        return "API_KEY";
    }

    public static void saveJsonToFile(String filename, String message){
        try {
            FileWriter myWriter = new FileWriter(filePath() + filename, false);
            myWriter.write(message);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static String retrieveDataFromList(List<String> list){
        StringBuilder result = new StringBuilder();
        String stringToRemoveAtFirst = "%2C";
        for(String s: list){
            result.append(stringToRemoveAtFirst).append(s);
        }
        return result.delete(0, stringToRemoveAtFirst.length()).toString();
    }

    public static void openFile(String filename) throws IOException {
        File file = new File(filePath() + filename);

        if(!Desktop.isDesktopSupported()){
            System.out.println("Desktop is not supported");
            return;
        }

        Desktop desktop = Desktop.getDesktop();
        if(file.exists()) desktop.open(file);
    }

    public static String getCurrentDate(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();

        return dtf.format(now);
    }

    private static String filePath(){
        return "/Users/tandanhduong/Desktop/CurrencyConverterApp/target/output/";
    }
}
