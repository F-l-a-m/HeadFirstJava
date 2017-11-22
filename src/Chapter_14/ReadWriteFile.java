package Chapter_14;

import java.io.*;
import java.util.*;

public class ReadWriteFile {

    public String readTextFile(File file) {
        String output = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                line += "\n";
                output += line;
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return output;
    }

    public void writeTextFile(File aFile, String aString) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(aFile));
            writer.write(aString);
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
