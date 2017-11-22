// Read and write text file tests
package Chapter_14;

import java.io.*;

public final class ReadWriteTest {

    public static void testRead() { //Read a text file to console
        ReadWriteFile f = new ReadWriteFile();
        String text;
        File file = new File("LoremIpsum.txt");
        text = f.readTextFile(file);
        System.out.println(text);
    }

    public static void testWrite() { //Write a text file
        String str = "First line,\nSecond line,\nThird line.";
        ReadWriteFile f = new ReadWriteFile();
        File file = new File("testWriteMethod.txt");
        f.writeTextFile(file, str);
    }
}
