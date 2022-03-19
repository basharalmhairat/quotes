package quoting;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Quote {
    public String[] tags;
    public String author;
    public String likes;
    public String text;
    public String quote;

    public Quote() {}

    public Quote(String text)
    {
        this.tags = null;
        this.author = "George Orwell";
        this.likes = null;
        this.text = text;
    }

    public Quote(String[] tags, String author, String likes, String text)
    {
        this.tags = tags;
        this.author = author;
        this.likes = likes;
        this.text = text;
    }

    public static String QuoteView(String path) throws Exception
    {
        Gson gson = new Gson();

        BufferedReader file = new BufferedReader(new FileReader(path));
        Quote[] quotesFromFiles = gson.fromJson(file, Quote[].class);

        int randomIndex = (int)(Math.random() * quotesFromFiles.length);
        String result = quotesFromFiles[randomIndex].toString();

        return result;
    }

    public static Quote readFromAPI(String urlPath, String backupFilePath)
    {
        StringBuilder content = new StringBuilder();
        Quote newQuote = null;

        try
        {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;

            while ((inputLine = input.readLine()) != null) {
                content.append(inputLine);
            }
            input.close();

            newQuote = new Quote(content.toString());

            try
            {
                writeToFile(backupFilePath, newQuote);
            }
            catch (Exception errorFile)
            {
                System.err.println("errorFile");
            }
        }
        catch (IOException errorAPI)
        {
            System.err.println("No internet connection.");
            try
            {
                newQuote = new Quote(QuoteView(backupFilePath));
            }
            catch (Exception errorFile)
            {
                System.err.println("errorFile");
            }
        }

        return newQuote;
    }

    public static void writeToFile(String filepath, Quote quote) throws Exception
    {
        Gson gson = new Gson();

        BufferedReader file = new BufferedReader(new FileReader(filepath));

        TypeToken<ArrayList<Quote>> token = new TypeToken<ArrayList<Quote>>(){};

        ArrayList<Quote> quotesFromFiles = gson.fromJson(file, token.getType());

        quotesFromFiles.add(quote);

        BufferedWriter writer = new BufferedWriter(new FileWriter(filepath));

        writer.write(gson.toJson(quotesFromFiles));

        writer.close();
        file.close();
    }

    @Override
    public String toString() {
        return String.format("%s Said: %s",author, text );
    }

}
