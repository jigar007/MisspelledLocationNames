import java.io.*;
import java.util.*;

/**
 * Created by jamesMAC on 25/08/2016.
 */
public class sensitization_location {

    private String fileName = "locations.txt";
    File locationFile = new File(fileName);

    public void readFile() throws IOException {

        try {
            if (!locationFile.exists()) {
                locationFile.createNewFile();
            }
            BufferedReader br = new BufferedReader(new FileReader("US-loc-names.txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("locations.txt"));
            Set<String> lines = new HashSet<String>(); // for remove duplicates

            String line;
            int i = 0;

            while ((line = br.readLine()) != null) {
                i++;
                line = line.replaceAll("([^\\w\\s])|(_)", "");  // Special characters
                line = line.replaceAll("\\w+\\d{2}\\b", "");    // Last 2 chars are numbers
                line = line.replaceAll("\\d{2}\\w+","");       // First 2 numbers + any length of string
                line = line.replaceAll("\\d{2}","");
                line = line.replaceAll("(\\s{2,})", " ");    // Combine 2 or more whitespce into one
                lines.add(line.trim());                      // Removes extra white spaces
            }

            for (String unique : lines) {
                writer.write(unique);
                writer.newLine();
            }

            System.out.println("number of line:" + i);
            br.close();
            writer.close();


        } catch (Exception e) {
            System.out.println(e);
        } finally {

        }
    }

    public void sortfile() throws IOException {

        FileReader fileReader = new FileReader("locations.txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String inputLine;
        List<String> lineList = new ArrayList<String>();
        while ((inputLine = bufferedReader.readLine()) != null) {
            lineList.add(inputLine);
        }
        fileReader.close();

        Collections.sort(lineList);

        FileWriter fileWriter = new FileWriter("locations.txt");
        PrintWriter out = new PrintWriter(fileWriter);
        for (String outputLine : lineList) {
            out.println(outputLine);
        }
        out.flush();
        out.close();
        fileWriter.close();
    }

}
