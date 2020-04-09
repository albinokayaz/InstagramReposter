package yt.richard.igrepost.utils;

import org.brunocvcunha.instagram4j.requests.InstagramUploadPhotoRequest;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static yt.richard.igrepost.Main.instagram;

public class MemeUtil {

     static List<BufferedImage> postedMemes = new ArrayList<>();

    private static BufferedImage getMeme() throws IOException {
        InputStream is = new URL("https://meme-api.herokuapp.com/gimme").openStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        JSONObject yot = new JSONObject(sb.toString());
        BufferedImage meme = null;
        URL url = new URL(yot.get("url").toString());
        meme = ImageIO.read(url);
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
        BufferedImage tempMeme = getMeme();
        File tempImage = new File("temp.jpg");
        ImageIO.write(tempMeme, "jpg", tempImage);
        String status = instagram.sendRequest(new InstagramUploadPhotoRequest(tempImage, "yes thats actually a very good meme I like to look at when I not am gameing!", "1")).getMessage();
        tempImage.delete();
        if(status != null) postMeme();
    }
}
