import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        String nbpJson = downloadJsonFromURL("http://api.nbp.pl/api/exchangerates/rates/a/eur?format=json");
        Gson gson = new Gson();
        Currency eurCurrency = gson.fromJson(nbpJson, Currency.class);

        double eurRateValue = eurCurrency.rates[0].mid;
        System.out.println("Today for 1 EUR you'll get " + eurRateValue + " PLN");
    }

    public static String downloadJsonFromURL(String urlText) {
        try {
            URL myUrl = new URL(urlText);
            StringBuilder jsonText = new StringBuilder();
            try (InputStream myInputStream = myUrl.openStream();
                 Scanner scanner = new Scanner(myInputStream)) {
                while (scanner.hasNextLine()) {
                    jsonText.append(scanner.nextLine());
                }
                return jsonText.toString();
            }
        } catch (IOException e) {
            System.err.println("Failed to get content from URL " + urlText + " due to exception: " + e.getMessage());
            return null;
        }
    }
}