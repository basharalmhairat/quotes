package quoting;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class Main {
    public static String quotesView(String path) throws Exception
    {
        Gson gson = new Gson();
        BufferedReader file = new BufferedReader(new FileReader(path));
        Quote[] quotesFromFiles = gson.fromJson(file, Quote[].class);

        int randomQ = (int)(Math.random() * quotesFromFiles.length);
        String result = quotesFromFiles[randomQ].toString();
        System.out.println(result);
        return result;
    }
    public static void main(String[] args) throws Exception {

        URL quoteURL = new URL("http://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en");

        HttpURLConnection quoteConnection = (HttpURLConnection) quoteURL.openConnection();

        quoteConnection.setRequestMethod("GET");

        InputStreamReader quoteInputStreamReader = new InputStreamReader(quoteConnection.getInputStream());
        BufferedReader quoteBufferedReader = new BufferedReader(quoteInputStreamReader);
        String quoteOnline = quoteBufferedReader.readLine();
        System.out.println(quoteOnline);

        Gson gson = new Gson();
        Quote quoteFromURL = gson.fromJson(quoteOnline, Quote.class);
        System.out.println(quoteFromURL);

        File quotingOnline = new File("./quotingOnline.json");
        try (FileWriter dittoFileWriter = new FileWriter(quotingOnline)) {
            gson.toJson(quoteFromURL, dittoFileWriter);
        }
        System.out.println(quotesView("./src/main/resources/quotes.json"));

    }
}