import java.util.*;
import java.io.*;
import java.lang.*;

public class Main {

    public static void main(String[] args) {

        sensitization_location loc = new sensitization_location();
        sensitization_tweet tweet1 = new sensitization_tweet();

       Main self = new Main();

        try {
            long startTime = System.currentTimeMillis();

            loc.readFile();
            loc.sortfile();

            tweet1.senitizeTweets();
            tweet1.countword();
            tweet1.removeStopWord();

            self.runGed();
            self.runSoundx();


            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println(totalTime);
        } catch (Exception e) {

        }
    }

    public void runSoundx() throws IOException {
        File resultFileGED = new File("result_Soundex.txt");
        try {
            if (!resultFileGED.exists()) {
                resultFileGED.createNewFile();
            }

            BufferedReader tweetRead = new BufferedReader(new FileReader("tweets_final.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("result_Soundex.txt"));

            String line, locationLine;
            int i = 0;
            while ((line = tweetRead.readLine()) != null) {

                i++;
                StringTokenizer str = new StringTokenizer(line);

                while (str.hasMoreTokens()) {
                    String token = str.nextToken();

                    if (!Character.isDigit(token.charAt(0))) {

                        BufferedReader locationRead = new BufferedReader(new FileReader("locations.txt"));

                        int j = 0;
                        while ((locationLine = locationRead.readLine()) != null) {

                            StringTokenizer strLoc = new StringTokenizer(locationLine);

                            boolean flag = true;
                            int count = 0;
                            int tokenCount = strLoc.countTokens();


                            while (strLoc.hasMoreTokens() && flag == true && tokenCount > 0 ) {
                                String locationName = strLoc.nextToken();

                                flag=checkSoundex(token,locationName);

                                j++;
                                if (flag == true) {
                                    count++;
                                }
                                if (count == tokenCount) {
                                    System.out.println(line);
                                    System.out.println(locationLine);
                                    System.out.println(token + ">" + locationName + ">" + count + ">" + tokenCount);
                                    System.out.println();

                                    writer.write(line);
                                    writer.newLine();
                                    writer.write(locationLine);
                                    writer.newLine();
                                    writer.write(token + ">" + locationName + ">" + count + ">" + tokenCount);
                                    writer.newLine();
                                }
                            }
                        }
                        locationRead.close();
                    }
                }
            }

            tweetRead.close();
            writer.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
    }

    public void runGed() throws IOException {
        File resultFileGED = new File("result_GED.txt");
        try {
            if (!resultFileGED.exists()) {
                resultFileGED.createNewFile();
            }

            BufferedReader tweetRead = new BufferedReader(new FileReader("tweets_final.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("result_GED.txt"));

            String line, locationLine;
            int i = 0;
            while ((line = tweetRead.readLine()) != null) {

                i++;
                StringTokenizer str = new StringTokenizer(line);

                while (str.hasMoreTokens()) {
                    String token = str.nextToken();

                    if (!Character.isDigit(token.charAt(0))) {

                        BufferedReader locationRead = new BufferedReader(new FileReader("locations.txt"));

                        int j = 0;
                        while ((locationLine = locationRead.readLine()) != null) {

                            StringTokenizer strLoc = new StringTokenizer(locationLine);

                            boolean flag = true;
                            int count = 0;
                            int tokenCount = strLoc.countTokens();


                            while (strLoc.hasMoreTokens() && flag == true && tokenCount > 0 ) {
                                String locationName = strLoc.nextToken();

                                flag = checkGED(token, locationName);

                                j++;
                                if (flag == true) {
                                    count++;
                                }
                                if (count == tokenCount) {
                                    System.out.println(line);
                                    System.out.println(locationLine);
                                    System.out.println(token + ">" + locationName + ">" + count + ">" + tokenCount);
                                    System.out.println();

                                    writer.write(line);
                                    writer.newLine();
                                    writer.write(locationLine);
                                    writer.newLine();
                                    writer.write(token + ">" + locationName + ">" + count + ">" + tokenCount);
                                    writer.newLine();
                                }
                            }
                        }
                        locationRead.close();
                    }
                }
            }

            tweetRead.close();
            writer.close();

        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
    }

    public boolean checkSoundex(String token, String location){

        Soundex s = new Soundex();
        return s.computesoundex(token).equals(s.computesoundex(location));
    }

    public boolean checkGED(String token, String location) {

        if (token.charAt(0) == location.charAt(0)) {

            int  tokenLength = token.length();

            edit_distance gedObj = new edit_distance(token, location);
            int ged = gedObj.fillScoreArray();

            if (tokenLength < 3 && ged == 0) {
                return true;
            } else if (tokenLength < 5 && tokenLength > 2 && ged < 2) {
               // System.out.println(token+">"+tokenLength);
                return true;
            } else if (tokenLength <6 && tokenLength>4 && ged < 3) {
              //  System.out.println(token+">"+tokenLength);
                return true;
            } else if (tokenLength < 8  && tokenLength>5 && ged < 4) {
              //  System.out.println("3rd"+token+">"+tokenLength);
                return true;
            } else if (tokenLength < 10 && tokenLength > 6 && ged < 5) {
                return true;
            } else if (tokenLength > 9 && ged < 6) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
