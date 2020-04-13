package yt.richard.igrepost.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HashtagUtil {

  public static List<String> getHashtags() throws IOException {
    List<String> hashtags = new ArrayList<>();
    Document doc = Jsoup.connect("https://app.sistrix.com/app_instagram/_result/lang/de?tag=meme").get();
    Elements tagsHTML = doc.getElementsByClass("instatag instatext");
    for (Element instatag : tagsHTML) {
      hashtags.add(instatag.html());
    }
    return hashtags;
  }

}
