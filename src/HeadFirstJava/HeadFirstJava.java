package HeadFirstJava;

import Chapter_01.*;
import Chapter_02.*;
import Chapter_03.*;
import Chapter_04.*;
import Chapter_05.*;
import Chapter_06.*;
import Chapter_07.*;
import Chapter_08.*;
import Chapter_10.*;
import Chapter_11.*;
import Chapter_12.*;
import Chapter_13.*;
import Chapter_14.*;
import Chapter_15.*;
import Chapter_16.*;
import Chapter_18.*;
import java.rmi.*;

public class HeadFirstJava {

    public static void main(String[] args) {

        //Page_14.BeerSong();
        //Page_20.Abcd();
        //Page_43.DrumKitTest();
        //Page_44.Echo();
        //Page_63.BooksTestDrive();
        //Page_64.Islands();
        //Page_65.PoolPuzzle();
        //Page_88.Copy();
        //Page_88.ClockTestDrive();
        //Page_91.Puzzle4();
        //SimpleDotComGame.SimpleDotComTestDrive();
        //SimpleDotComGame.PlayGame();
        //Page_119.CodeMagnets();
        //DotComGame.PlayGame();
        //Page_161.CodeMagnets();
        //Page_192.MixedMessages();
        //Page_194.TestBoats();
        //Page_234.PoolPuzzle();
        //Ch10_DateTime.DateAndTimeFun();
        //Page_310.BeTheCompiler();
        //Page_312.CodeMagnets();
        //String[] arg = {"102", "30"};
        //Page_342.MiniMusicCmdLine(arg);
        //String[] arg = {"yes", "no"};
        //Page_349.CodeMagnets(arg);
        //GUI_Fun.start();
        //Page_384.start();
        //Page_389.start();
        //Page_390.start();
        //Page_391.start();
        //Page_395.beTheCompiler();
        //Page_396.poolPuzzle();
        
        // Chapter 13, Beat box
        //new BeatBoxGUI().buildGUI();
        
        // Chapter 14
        //ReadWriteTest.testRead();
        //ReadWriteTest.testWrite();
        //VerySimpleTextEditor t = new VerySimpleTextEditor();
        //t.buildGUI();
        //Page_449.launchQuizCardGame();
        //Page_467.codeMagnets();
       
        /* 15th Chapter */
        /*
        Runnable threadJob = new DailyAdviceServer();
        Thread serverThread = new Thread(threadJob);
        serverThread.start();
        System.out.println("Back in main");
        DailyAdviceClient client = new DailyAdviceClient();
        client.go();  */
        /*
        Runnable threadJob = new VerySimpleChatServer();
        Thread serverThread = new Thread(threadJob);
        serverThread.start();
        SimpleChatClient chatClient = new SimpleChatClient();
        chatClient.go(); */
        //Page_524.codeMagnets();
        
        // Final Beat box
        Runnable threadJob = new MusicServer();
        Thread serverThread = new Thread(threadJob);
        serverThread.start();
        System.out.println("Launching server, please wait ...");
        try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) { }
        System.out.println("Server is up");
        BeatBoxFinal beatBox = new BeatBoxFinal();
        beatBox.startUp("Flam");
        
        /* 16 th Chapter */
        //Sorting.compareSongs();
        
        
        /* 18 th Chapter */
        
        /*
        try {
            Naming.rebind("ServiceServer", new ServiceServerImpl());
        } catch (Exception ex){
            ex.printStackTrace();
        }
        System.out.println("Remote service is running");
        
        ServiceBrowser sb = new ServiceBrowser();
        sb.buildGUI();*/
        
        // Local
        //LocalServiceBrowser ls = new LocalServiceBrowser();
        //ls.buildGUI();
        // to do
        // fix close option
        // add dice
        // add music stop button
        // dayofweek fonts and output format
        // dice add "num of dices to roll" label
    }
}
