import java.io.*;
import java.util.*;

/**
 * Created by jamesMAC on 28/08/2016.
 */
public class sensitization_tweet {

    // any http and www link is remove
    public void senitizeTweets() throws IOException {

        File tweetFile = new File("tweets.txt");

        try {
            if (!tweetFile.exists()) {
                tweetFile.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader("thakkarj_tweets.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("tweets.txt"));
            String line;

            while ((line = br.readLine()) != null) {

                line = line.replaceAll("(https?://.*?\\s+)|(www.*?\\s)|([^\\w \\t:-])|(#)", "");
                // removes http and www and all special characters
                writer.write(line);
                writer.newLine();

            }
            br.close();
            writer.close();

        } catch (Exception e) {

        }
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public void countword() throws IOException {

        File wordCount = new File("word_count1.txt");

        if (!wordCount.exists()) {
            wordCount.createNewFile();
        }

        Scanner input = new Scanner(new File("tweets.txt"));
        BufferedWriter wc = new BufferedWriter(new FileWriter("Word_count1.txt"));

        // count occurrences
        Map<String, Integer> wordCounts = new TreeMap<String, Integer>();
        while (input.hasNext()) {

            String next = input.next().toLowerCase();
            if (!wordCounts.containsKey(next)) {
                wordCounts.put(next, 1);
            } else {
                wordCounts.put(next, wordCounts.get(next) + 1);
            }
        }

        wordCounts = sensitization_tweet.sortByValue(wordCounts);

        // get cutoff and report frequencies
        System.out.println("Total words = " + wordCounts.size());
        int min = 500, wordFrequency = 0, wordLessThan500 = 0;

        for (String word : wordCounts.keySet()) {
            int count = wordCounts.get(word);


            if (!Character.isDigit(word.charAt(0)) && min < count) {

                wc.write("\n" + word);
                System.out.println(count + "\t" + word);
                wordFrequency = wordFrequency + count;
                wordLessThan500++;

            }

        }
        System.out.println("word frq= " + wordFrequency);
        System.out.println("word less than 500 =" + wordLessThan500);
        wc.close();

    }

    public void removeStopWord() throws IOException {

        File tweetFile = new File("tweets_final.txt");

        try {
            if (!tweetFile.exists()) {
                tweetFile.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader("tweets.txt"));

            BufferedWriter writer = new BufferedWriter(new FileWriter("tweets_final.txt"));

            String line;

            int i = 0, j = 0;


            while ((line = br.readLine().toLowerCase()) != null) {

                BufferedReader stop = new BufferedReader(new FileReader("stop_words.txt"));
                String stopLine;
                while ((stopLine = stop.readLine()) != null) {
                    line = line.replaceAll("\\b" + stopLine + "\\b", "");
                }
                line = line.replaceAll("(\\s{2,})", " ");    // Combine 2 or more whitespce into one
                System.out.println(line.toLowerCase());
                writer.write(line);
                writer.newLine();

                stop.close();
            }
            br.close();
            writer.close();

        } catch (Exception e) {

        }

    }

}
