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

    private static final String regexCharactersToAvoid = "[:,.\"'<>()?!/`”„{}]";

    public static void main(String[] args) {
        runApp();
    }

    /**
     * application process
     */
    private static void runApp() {
        fillCommonList();

        Scanner scanner = new Scanner(System.in);
        String url = "";
        String specificExpression = "";

        List<Website> websites = new ArrayList<>();
        websites.add(new Website("Onet","http://www.onet.pl/", "span.title"));
        websites.add(new Website("Interia", "http://www.interia.pl/", "a.tiles-a"));
        websites.add(new Website("WP", "http://www.wp.pl/", "section#section_topnews"));

        System.out.println("Welcome in SearchPopularWebsiteWords.");
        System.out.println("Please type website's name from the list: ");

        boolean flag = true;

        while(flag) {

            for(Website web : websites) {
                System.out.println("-> " + web.getName());
            }

            String scan = scanner.nextLine();

            for (Website web : websites) {
                if (web.getName().equals(scan)) {
                    url = web.getUrl();
                    specificExpression = web.getSpecificExpression();

                    System.out.println("Plase enter the amount of words to display: ");
                    int numberToDisplay = scanIntFromUser(scanner);

                    // connect, download, save to file
                    String fileName = "popular_words.txt";
                    List<String> wordFromWebsite = setPopularWords(connectDownload(url, specificExpression));
                    saveToFile(fileName, wordFromWebsite);

                    // read from file, compare with common words, save to file
                    String fileNameFiltered = "filtered_popular_words.txt";
                    List<String> wordFromWebsiteFiltered = compareWithCommon(listFromFile(fileName));
                    saveToFile(fileNameFiltered, wordFromWebsiteFiltered);

                    //count the most popular, display specific number first ones
                    List<String> countMostPopularWords = countPopularWords(wordFromWebsiteFiltered);
                    displayTheMostPopular(countMostPopularWords, numberToDisplay);
                    break;

                } else if(scan.equals("quit")) flag = false;
            }

            if(flag) System.out.println("Please enter name of website from the list or type (quit) to exit app");
            else System.out.println("Thank You, app is closing.");
        }
        scanner.close();
    }

    /**
     *
     * @param scanner
     * @return integer from user
     */
    private static int scanIntFromUser(Scanner scanner) {
        Integer number = null;

        while(number == null || number < 1) {
            String text = scanner.nextLine();
            try {
                number = Integer.parseInt(text);
                if(number < 1) {
                    System.out.println("Please type number greater than 0");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please type valid number");
            }
        }
        return number;
    }

    /**
     * Display specific amount the first most popular words from input list.
     * @param listToDisplay words from this list will be displayed
     * @param numberToDisplay specific number of displayed words
     */
    private static void displayTheMostPopular(List<String> listToDisplay, int numberToDisplay) {
        if(numberToDisplay > listToDisplay.size()) {
            numberToDisplay = listToDisplay.size();
        }

        System.out.println("======================================");
        System.out.println("First " + numberToDisplay + " most popular words: ");
        System.out.println("--------------------------------------");
        for(int i = 0; i < numberToDisplay; i++) {
            String prefixWord = listToDisplay.get(i);
            int indexUnderscore = prefixWord.indexOf("_");
            String prefix = prefixWord.substring(0, indexUnderscore);
            int prefixNumber = Integer.parseInt(prefix);
            String word = prefixWord.substring(indexUnderscore + 1);
            System.out.printf("%-10s \t | amount: \t %-3d \n", word, prefixNumber);
        }
        System.out.println("--------------------------------------");
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
        commonWords.add("tylko");
    }

    /**
     * Remove common words and sort them from Z to A
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
    private static void saveToFile(String fileName, List<String> sortedWords) {

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
            System.out.println(fileName + " was created.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("File could not be written.");
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
     * Every word from List lines will be separated from non alphanumeric signs
     * contain in private static final regexCharactersToAvoid field.
     * @param lines List of lines downloaded from website
     * @return list of sorted words
     */
    private static List<String> setPopularWords(List<String> lines) {

        List<String> words = new ArrayList<>();
        for(String line : lines) {
            line = line.trim();
            StringTokenizer sToken = new StringTokenizer(line);
            while(sToken.hasMoreTokens()) {
                String word = sToken.nextToken();
                if(word.length() > 3) {
                    word = word.replaceAll(regexCharactersToAvoid, "");
                    words.add(word);
                }
            }
        }
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

    public static class Website {
        private String name;
        private String url;
        private String specificExpression;

        public Website(String name, String url, String specificExpression) {
            this.name = name;
            this.url = url;
            this.specificExpression = specificExpression;
        }

        public String getName() {
            return name;
        }

        public String getUrl() {
            return url;
        }

        public String getSpecificExpression() {
            return specificExpression;
        }
    }

}
