package yt.richard.igrepost;

import org.brunocvcunha.instagram4j.Instagram4j;
import java.io.*;
import java.util.Properties;
import java.util.Random;

import static yt.richard.igrepost.utils.MemeUtil.postMeme;


public class Main {

    public static Instagram4j instagram;
    public static Properties config;

    public static void main(String[] args) {
        try {
            config.loadFromXML(new FileInputStream("config.xml"));

            instagram = Instagram4j.builder().username(config.getProperty("username")).password(config.getProperty("password")).build();
            instagram.setup();
            instagram.login();

            run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void run() throws IOException {
        postMeme();
        Random r = new Random();
        int randomTime = r.nextInt(30) + 60;
        try {
            Thread.sleep(60000 * randomTime);
            run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
