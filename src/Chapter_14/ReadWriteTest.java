// Read and write text file tests
package Chapter_14;

import java.util.*;

public final class ReadWriteTest {
    
    public static void testRead() { //Read a text file to console
        ReadWriteFile f = new ReadWriteFile();
        ArrayList<String> textFromFile;
        textFromFile = f.readTextFile("LoremIpsum.txt");
        for (String line : textFromFile) {
            System.out.println(line);
        }
    }
    
    public static void testWrite() { //Write a text file
        ArrayList<String> textToFile = new ArrayList<>();
        textToFile.add("First line,");
        textToFile.add("Second line,");
        textToFile.add("Third line,");
        ReadWriteFile f = new ReadWriteFile();
        f.writeTextFile("testWrite.txt", textToFile);
    }
}
