package pl.coderslab.searchPopularWebsiteWords;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        runApp();
    }

    /**
     * application process
     */
    private static void runApp() {
        String url = "http://www.onet.pl/";
        String specificExpression = "span.title";

//        // display in console - test purpose 1
//        for(String title : connectDownload(url, specificExpression)){
//            System.out.println(title);
//        }

//        // display in console - test purpose 2
//        for(String word : sortPopularWords(connectDownload(url, specificExpression))){
//            System.out.println(word);
//        }

        // display in console - test purpose3
        for(String word : countPopularWords(sortPopularWords(connectDownload(url, specificExpression)))){
            System.out.println(word);
        }

    }

    /**
     * In return List every word has a specific prefix [0000_] where number means how often every word occurred
     * in List sortedWords.
     * @param sortedWords
     * @return List with descending most popular sorted words.
     */
    private static List<String> countPopularWords(List<String> sortedWords) {

        List<String> countedWords = new ArrayList<>();
        int counter = 1;

        for (int i = 0; i < sortedWords.size() - 1; i++) {
            // change comparated words to lowercase but only in if condition
            if(sortedWords.get(i).toLowerCase().equals(sortedWords.get(i + 1).toLowerCase())) {
                counter++;
            } else {
                String prefix = Integer.toString(counter);
                // left padging zeros up to 4 digits
                prefix = String.format("%04d", counter);
                countedWords.add(prefix + "_" + sortedWords.get(i));
                counter = 1;
            }
        }
        Collections.sort(countedWords);
        Collections.reverse(countedWords);
        return countedWords;
    }

    /**
     * Every word from List lines will be separated from non alphanumeric signs.
     * @param lines List of lines downloaded from website
     * @return list of sorted words
     */
    private static List<String> sortPopularWords(List<String> lines) {

        List<String> words = new ArrayList<>();
        for(String line : lines) {
            line = line.trim();
            StringTokenizer sToken = new StringTokenizer(line);
            while(sToken.hasMoreTokens()) {
                String word = sToken.nextToken();
                if(word.length() > 3) {
                    word = word.replaceAll("[:,.\"'<>()?!/]", "");
                    words.add(word);
                }
            }
        }
        Collections.sort(words);
        return words;
    }

    /**
     * Connect with website (url) and download specific text included in specific html attributes
     * @param url website address
     * @param specificExpression specific html attributes
     * @return list of lines from downloaded text
     */
    private static List<String> connectDownload(String url, String specificExpression) {
        List<String> titles = new ArrayList<>();
        Connection connect = Jsoup.connect(url);
        try{
            Document document = connect.get();
            Elements links = document.select(specificExpression);
            for(Element elem : links) {
                titles.add(elem.text());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return titles;
    }


}
