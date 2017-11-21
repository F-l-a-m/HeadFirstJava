package Chapter_14;

import java.io.*;
import java.util.*;

public class ReadWriteFile {

    public ArrayList<String> readTextFile(String filename) { //Returns an array of Strings
        ArrayList<String> text = new ArrayList<>();
        try {
            File aFile = new File(filename);
            BufferedReader reader = new BufferedReader(new FileReader(aFile));
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                text.add(line);
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return text;
    }

    public void writeTextFile(String fileName, ArrayList<String> text) {
        try {
            File aFile = new File(fileName);
            BufferedWriter writer = new BufferedWriter(new FileWriter(aFile));
            for (String line : text) {
                writer.write(line + "\n");
            }
            writer.close();
            System.out.println("New file was written to: ");
            System.out.println(aFile.getAbsolutePath());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void mkdir(String folderName) {
        File dir = new File(folderName);
        dir.mkdir();
        System.out.println(dir.getAbsolutePath());
    }
}
