package yt.richard.igrepost.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;

public class ConfigManager {

  public static Properties config = new Properties();

  public static void initiate() {
    try {
      File save = new File("config.xml");
      if (!save.exists()) {
        startSetup();
      } else {
        InputStream is = new FileInputStream("config.xml");
        config.clear();
        config.loadFromXML(is);
      }
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }

  private static void startSetup() {
    try {
      Scanner scanner = new Scanner(System.in);
      OutputStream os = new FileOutputStream("config.xml");
      System.out.println("Welcome to the setup!");

      System.out.println("Please enter the username for the bot account:");
      String username = scanner.next();
      config.setProperty("username", username);

      System.out.println("Please enter the password for the bot account:");
      String password = scanner.next();
      config.setProperty("password", password);

      System.out.println("What's the minimum post delay? (in minutes)");
      String mindelay = scanner.next();
      config.setProperty("mindelay", mindelay);

      System.out.println("What's the maximum post delay? (in minutes)");
      String maxdelay = scanner.next();
      config.setProperty("maxdelay", maxdelay);

      config.storeToXML(os, null);
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
  }
}
