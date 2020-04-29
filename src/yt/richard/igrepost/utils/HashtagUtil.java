package yt.richard.igrepost.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class HashtagUtil {

  public static List<String> getDynamicHashtags() throws IOException {
    List<String> hashtags = new ArrayList<>();
    String keywords = ConfigManager.config.getProperty("keywords");
    Document doc = Jsoup.connect("https://app.sistrix.com/app_instagram/_result/lang/de?tag=" + keywords).get();
    Elements tagsList = doc.getElementsByClass("instatag instatext");
    tagsList.forEach(tag -> hashtags.add(tag.html()));
    return hashtags;
  }

  public static List<String> getHashtags() {
    List<String> hashtags = new ArrayList<>();
    JSONArray tagsList = JsonUtil.retrieveJsonArray("https://richard.yt/instagram/hashtags.json");
    tagsList.forEach(tag -> hashtags.add((String) tag));
    return hashtags;
  }

}
