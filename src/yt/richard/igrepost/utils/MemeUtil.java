package yt.richard.igrepost.utils;

import static yt.richard.igrepost.Main.instance;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.brunocvcunha.instagram4j.requests.InstagramUploadPhotoRequest;
import org.json.JSONObject;

class Meme {
    private String title = null;
    private String url = null;
    private BufferedImage image = null;

    String getTitle() { return this.title; }
    String getURL() { return url; }
    BufferedImage getImage() { return this.image; }
    void setTitle(String title) { this.title = title; }
    void setURL(String url) { this.url = url; }
    void setImage(BufferedImage image) { this.image = image; }
}

public class MemeUtil {

    static List<String> postedMemes = new ArrayList<>();

    private static BufferedImage resize(BufferedImage img) {
        int new_width = 1000;
        int new_height = 1000;
        BufferedImage resizedImg = new BufferedImage(new_width, new_height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setBackground(Color.WHITE);
        g2.clearRect(0,0,new_width, new_height);
        g2.drawImage(img, 0, 0, new_width, new_height, null);
        g2.dispose();
        return resizedImg;
    }

    private static Meme getMeme() throws IOException {
        Logger.log("Getting new meme...");

        JSONObject json = JsonUtil.retrieveJsonObject("https://meme-api.herokuapp.com/gimme/dankmemes");

        Meme meme = new Meme();
        meme.setTitle(json.getString("title"));
        meme.setURL(json.getString("postLink"));
        meme.setImage(resize(ImageIO.read(new URL(json.getString("url")))));

        Logger.log("Checking if meme was already posted...");
        if(postedMemes.contains(meme.getURL())) {
            getMeme();
        } else {
            postedMemes.add(meme.getURL());
        }
        if(postedMemes.size() > 50) {
            postedMemes.remove(0);
        }

        return meme;
    }

    public static void postMeme() throws IOException {
        Meme tempMeme = getMeme();
        File tempImage = new File("temp.jpg");
        ImageIO.write(tempMeme.getImage(), "jpg", tempImage);
        Logger.log("Posting meme!");
        String hashtags = String.join(" ", HashtagUtil.getHashtags());
        String desc = tempMeme.getTitle() + "\nThis meme was taken from " + tempMeme.getURL() + "\n\n" + hashtags;
        String status = instance.sendRequest(new InstagramUploadPhotoRequest(tempImage, desc, null)).getMessage();
        //noinspection ResultOfMethodCallIgnored
        tempImage.delete();
        if(status != null)
            Logger.log("There was an error! (" + status + ")");
    }
}
