package quoting;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileReader;


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

        System.out.println(quotesView("./src/main/resources/quotes.json"));

    }
}