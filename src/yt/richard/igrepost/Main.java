package yt.richard.igrepost;

import static yt.richard.igrepost.utils.MemeUtil.postMeme;

import java.io.IOException;
import java.util.Random;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.brunocvcunha.instagram4j.Instagram4j;
import yt.richard.igrepost.utils.ConfigManager;
import yt.richard.igrepost.utils.InstagramUtil;
import yt.richard.igrepost.utils.Logger;


public class Main {

    public static Instagram4j instance;

    public static void main(String[] args) {
        try {
            LogManager.getRootLogger().setLevel(Level.OFF);

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
