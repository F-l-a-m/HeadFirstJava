package Chapter_16;

import java.util.*;
import java.io.*;

public final class Sorting {

    public static void compareSongs() {
        //Jukebox jb = new Jukebox();
        //jb.go();
        SortMountains sm = new SortMountains();
        sm.go();
    }
}

class Jukebox {

    ArrayList<Song> songList = new ArrayList<Song>();

    public void go() {
        getSongs();
        System.out.println("Not sorted");
        System.out.println(songList);
        Collections.sort(songList);
        System.out.println("Sort by title");
        System.out.println(songList);
        System.out.println("Sort by artist");
        ArtistCompare artistCompare = new ArtistCompare();
        Collections.sort(songList, artistCompare);
        System.out.println(songList);
        System.out.println("Sort by rating");
        RatingCompare ratingCompare = new RatingCompare();
        Collections.sort(songList, ratingCompare);
        System.out.println(songList);
    }

    void getSongs() {
        try {
            File file = new File("SongList.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                addSong(line);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void addSong(String lineToParse) {
        String[] tokens = lineToParse.split("/");
        Song nextSong = new Song(tokens[0], tokens[1], tokens[2], tokens[3]);
        songList.add(nextSong);
    }

    class ArtistCompare implements Comparator<Song> {

        @Override
        public int compare(Song one, Song two) {
            return one.getArtist().compareTo(two.getArtist());
        }
    }

    class RatingCompare implements Comparator<Song> {

        @Override
        public int compare(Song one, Song two) {
            int a = Integer.parseInt(one.getRating());
            int b = Integer.parseInt(two.getRating());
            return Integer.compare(a, b);
        }
    }
}

class Song implements Comparable<Song> {

    String title;
    String artist;
    String rating;
    String bpm;

    Song(String t, String a, String r, String b) {
        title = t;
        artist = a;
        rating = r;
        bpm = b;
    }

    @Override
    public int compareTo(Song s) {
        return title.compareTo(s.getTitle());
    }

    String getTitle() {
        return title;
    }

    String getArtist() {
        return artist;
    }

    String getRating() {
        return rating;
    }

    String getStarRating() {
        String stars = "";
        int r = Integer.parseInt(rating);
        for (int i = 0; i < r; i++) {
            stars += "*";
        }
        return stars;
    }

    String getBPM() {
        return bpm;
    }

    @Override
    public String toString() {
        char c = 9;
        String tab = Character.toString(c);
        return bpm + tab + getStarRating() + tab + artist + tab + title + "\n";
    }
}

class SortMountains {

    LinkedList<Mountain> mtn = new LinkedList<Mountain>();

    public void go() {
        mtn.add(new Mountain("Longs", 14255));
        mtn.add(new Mountain("Elbert", 14433));
        mtn.add(new Mountain("Maroon", 14156));
        mtn.add(new Mountain("Castle", 14265));

        System.out.println("As entered: " + mtn);
        NameCompare nc = new NameCompare();
        Collections.sort(mtn, nc);
        System.out.println("By name: " + mtn);
        HeightCompare hc = new HeightCompare();
        Collections.sort(mtn, hc);
        System.out.println("By height: " + mtn);
    }

    class NameCompare implements Comparator<Mountain> {

        @Override
        public int compare(Mountain mtn1, Mountain mtn2) {
            return mtn1.getName().compareTo(mtn2.getName());
        }
    }

    class HeightCompare implements Comparator<Mountain> {

        @Override
        public int compare(Mountain mtn1, Mountain mtn2) {
            return Integer.compare(mtn2.getHeight(), mtn1.getHeight());
        }
    }
}

class Mountain {

    String name;
    int height;

    Mountain(String n, int h) {
        name = n;
        height = h;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return name + " " + height;
    }
}
