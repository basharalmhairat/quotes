package quoting;

import static quoting.Quote.QuoteView;

public class Main {
    public static void main(String[] args) throws Exception {
        String FilePath = "./src/main/resources/quotes.json";
        String URLPath = "https://ron-swanson-quotes.herokuapp.com/v2/quotes";


            System.out.println(Quote.readFromAPI(URLPath, FilePath));
//            System.out.println(QuoteView("./src/main/resources/quotes.json"));



    }
}