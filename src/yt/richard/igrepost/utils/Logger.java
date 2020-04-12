package yt.richard.igrepost.utils;

import static yt.richard.igrepost.Main.instance;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger {

  public static void log(String message) {
    String timeStamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
    System.out.println("[" + timeStamp + "] " + instance.getUsername() + " (" + instance.getUserId() + ") >> " + message);
  }

}
