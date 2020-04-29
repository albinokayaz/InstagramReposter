package yt.richard.igrepost;

import static yt.richard.igrepost.utils.MemeUtil.postMeme;

import java.io.IOException;
import java.util.Random;
import org.apache.log4j.Level;
import org.brunocvcunha.instagram4j.Instagram4j;
import yt.richard.igrepost.utils.ConfigManager;
import yt.richard.igrepost.utils.InstagramUtil;
import yt.richard.igrepost.utils.Logger;


public class Main {

    public static Instagram4j instance;

    public static void main(String[] args) {
        try {
            org.apache.log4j.Logger.getLogger("org.brunocvcunha").setLevel(Level.OFF); // Disable i4j logs
            org.apache.log4j.Logger.getLogger("org.brunocvcunha.instagram4j").setLevel(Level.OFF);

            System.setProperty("http.agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.129 Safari/537.36");

            ConfigManager.initiate();

            instance = Instagram4j.builder()
                .username(ConfigManager.config.getProperty("username"))
                .password(ConfigManager.config.getProperty("password"))
                .build();
            instance.setup();
            InstagramUtil.login();

            run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void run() throws IOException {
        if(instance.isLoggedIn()) postMeme();

        Random r = new Random();
        int mindelay = Integer.parseInt(ConfigManager.config.getProperty("mindelay"));
        int maxdelay = Integer.parseInt(ConfigManager.config.getProperty("maxdelay"));
        int randomTime = r.nextInt(maxdelay - mindelay) + mindelay;

        Logger.log("Posting next meme in " + randomTime + " minutes.");
        new java.util.Timer().schedule(new java.util.TimerTask() {
            @Override
            public void run() {
                try { Main.run(); } catch (IOException e) { e.printStackTrace(); }
                }
            }, randomTime * 60000
        );
    }
}
