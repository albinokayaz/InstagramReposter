package yt.richard.igrepost.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonUtil {

  public static JSONObject retrieveJsonObject(String url) {
    try {
      return new JSONObject(getJSON(url));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static JSONArray retrieveJsonArray(String url) {
    try {
      return new JSONArray(getJSON(url));

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private static String getJSON(String url) throws IOException {
    InputStream is = new URL(url).openStream();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

}
