import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebCrawler {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final String VALID_WEB_URL_REGEX = "\"(?i)^(?:(?:https?|ftp)://)(?:\\\\S+(?::\\\\S*)?@)?(?:(?!(?:10|127)(?:\\\\.\\\\d{1,3}){3})(?!(?:169\\\\.254|192\\\\.168)(?:\\\\.\\\\d{1,3}){2})(?!172\\\\.(?:1[6-9]|2\\\\d|3[0-1])(?:\\\\.\\\\d{1,3}){2})(?:[1-9]\\\\d?|1\\\\d\\\\d|2[01]\\\\d|22[0-3])(?:\\\\.(?:1?\\\\d{1,2}|2[0-4]\\\\d|25[0-5])){2}(?:\\\\.(?:[1-9]\\\\d?|1\\\\d\\\\d|2[0-4]\\\\d|25[0-4]))|(?:(?:[a-z\\\\u00a1-\\\\uffff0-9]-*)*[a-z\\\\u00a1-\\\\uffff0-9]+)(?:\\\\.(?:[a-z\\\\u00a1-\\\\uffff0-9]-*)*[a-z\\\\u00a1-\\\\uffff0-9]+)*(?:\\\\.(?:[a-z\\\\u00a1-\\\\uffff]{2,}))\\\\.?)(?::\\\\d{2,5})?(?:[/?#]\\\\S*)?$\"\n";
    private String startingNode;
    private Set<String> sites = new HashSet<>();
    private Queue<String> websitesToVisit = new LinkedList<>();


    public WebCrawler(String startingNode) {
        this.startingNode = startingNode;
        websitesToVisit.add(startingNode);
    }

    public void startCrawling() throws IOException {
        URL uri = null;
        BufferedReader bufferedReader = null;
        while (!websitesToVisit.isEmpty()) {
            try {
                String actualNode = websitesToVisit.poll();
                uri = new URL(actualNode);
                bufferedReader = new BufferedReader(new InputStreamReader(uri.openStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String tmp = null;
                while ((tmp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(tmp);
                }

                Pattern website = Pattern.compile(VALID_WEB_URL_REGEX);
                Matcher webMatcher = website.matcher(stringBuilder.toString());
                while (webMatcher.find()) {
                    String found = webMatcher.group();
                    if (!sites.contains(found)) {
                        sites.add(found);
                        System.out.println(found);
                        websitesToVisit.add(found);
                    }

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            bufferedReader.close();

        }
    }


    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    private static boolean validateUrl(String url) {
        Matcher matcher = Pattern.compile(VALID_WEB_URL_REGEX, Pattern.CASE_INSENSITIVE).matcher(url);
        return matcher.find();
    }
}