package yt.richard.igrepost.utils;

import static yt.richard.igrepost.Main.instance;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import org.brunocvcunha.instagram4j.requests.InstagramUploadPhotoRequest;
import org.json.JSONObject;

class Meme {
    String title = null;
    String url = null;
    BufferedImage image = null;

    String getTitle() { return this.title; }
    String getURL() { return url; }
    BufferedImage getImage() { return this.image; }
}

public class MemeUtil {

    static List<Meme> postedMemes = new ArrayList<>();

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
        InputStream is = new URL("https://meme-api.herokuapp.com/gimme/dankmemes").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        JSONObject yot = new JSONObject(sb.toString());

        Meme meme = new Meme();
        meme.title = yot.getString("title");
        meme.url = yot.getString("postLink");
        meme.image = resize(ImageIO.read(new URL(yot.getString("url")))); // resize image to 1000x1000 to not encounter format problems

        Logger.log("Checking if meme was already posted...");
        if(postedMemes.contains(meme)) {
            getMeme();
        } else {
            postedMemes.add(meme);
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
        String desc = tempMeme.getTitle() + "\nThis meme was taken from " + tempMeme.getURL() + "\n\n#pronouncingthingsincorrectly #stolenmeme #whatsgood #rocketfuelcantmeltdankmemes #stolenmemes #newmemes #cancerousmemes #wholesomememes #yeetmemes #aesthetic #ifunny #goodmemes #funnyvids #hilariousvideos #sendthistoyourcrushwithnocontext #funnyedits #noteforself #odlysatisfying #dankmemes #nichememesdaily";
        String status = instance.sendRequest(new InstagramUploadPhotoRequest(tempImage, desc, null)).getMessage();
        //noinspection ResultOfMethodCallIgnored
        tempImage.delete();
        if(status != null)
            Logger.log("There was an error! (" + status + ")");
    }
}
