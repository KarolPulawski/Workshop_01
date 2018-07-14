package pl.coderslab.searchPopularWebsiteWords;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static ArrayList<String> commonWords = new ArrayList<>();

    public static void main(String[] args) {
        runApp();
    }

    /**
     * application process
     */
    private static void runApp() {
        fillCommonList();
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

//        // display in console - test purpose3
//        for(String word : countPopularWords(sortPopularWords(connectDownload(url, specificExpression)))){
//            System.out.println(word);

        // connect, download, compare with common words, sort descending
        String fileName = "popular_words.txt";
        System.out.println(saveToFile(fileName, sortPopularWords(connectDownload(url, specificExpression))));

    }

    /**
     * Read all data from file.
     * @param fileName name of file from main catalog
     * @return List of String with all read data from file.
     */
    private static List<String> listFromFile(String fileName) {

        List<String> list = new ArrayList<>();
        File fileToRead = new File(fileName);

        try {
            Scanner scan = new Scanner(fileToRead);
            while(scan.hasNextLine()) {
                list.add(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File does not exists.");
        }
        return list;
    }

    /**
     * Fill List with ver common words to elminate them from popular words List.
     */
    private static void fillCommonList() {
        commonWords.add("oraz");
        commonWords.add("ponieważ");
        commonWords.add("jeżeli");
        commonWords.add("aczkolwiek");
        commonWords.add("pomimo");
        commonWords.add("lecz");
        commonWords.add("czyli");
        commonWords.add("jest");
        commonWords.add("dlatego");
        commonWords.add("właśnie");
        commonWords.add("teraz");
        commonWords.add("odkąd");
        commonWords.add("zaraz");
        commonWords.add("kiedy");
        commonWords.add("kiedyś");
        commonWords.add("kogo");
        commonWords.add("więc");
        commonWords.add("akurat");
    }

    /**
     *
     * @param listToCompare list to compare
     * @return List without common words from commonWords List.
     */
    private static List<String> compareWithCommon(List<String> listToCompare) {
        List<String> finalList = new ArrayList<>();

        for(String wordToCheck : listToCompare) {
            if(!commonWords.contains(wordToCheck)) {
                finalList.add(wordToCheck);
            }
        }
        Collections.sort(finalList);
        Collections.reverse(finalList);
        return finalList;
    }

    /**
     * Input List sortedList will be written in main catalog with name fileName.
     * @param fileName file name with extension
     * @param sortedWords
     * @return true if completed, false if failed
     */
    private static boolean saveToFile(String fileName, List<String> sortedWords) {

        Path filePath = Paths.get("./" + fileName);

        // check if file exists
        if(!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            Files.write(filePath, sortedWords);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File could not be written.");
            return false;
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
