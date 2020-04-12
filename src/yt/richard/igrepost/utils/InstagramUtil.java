package yt.richard.igrepost.utils;

import static yt.richard.igrepost.Main.instance;

import java.io.IOException;
import java.util.Scanner;
import org.brunocvcunha.instagram4j.requests.InstagramGetChallengeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSelectVerifyMethodRequest;
import org.brunocvcunha.instagram4j.requests.InstagramSendSecurityCodeRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramLoginResult;

public class InstagramUtil {

  public static void login() throws IOException {
    InstagramLoginResult status = instance.login();
    if(status.getMessage() == null) return; // Everything worked
    if(status.getMessage().equals("challenge_required")) {
      Scanner scanner = new Scanner(System.in);

      Logger.log("Instagram blocked your login!");
      String challengeURL = status.getChallenge().getApi_path().substring(1);

      InstagramGetChallengeRequest getChallenge = new InstagramGetChallengeRequest(challengeURL);
      InstagramSelectVerifyMethodRequest getVerificationCode = new InstagramSelectVerifyMethodRequest(challengeURL, 1);
      instance.sendRequest(getChallenge);
      instance.sendRequest(getVerificationCode);

      Logger.log("Please enter the verification code sent to your email:");
      String code = scanner.next();
      Logger.log("Verifying with code " + code);
      InstagramSendSecurityCodeRequest request = new InstagramSendSecurityCodeRequest(challengeURL, code);
      InstagramLoginResult result = instance.sendRequest(request);

      if(result.getLogged_in_user() == null)
        Logger.log("Couldn't log you in! Did you enter the correct code?");
    } else if (status.getMessage().equals("The password you entered is incorrect. Please try again or log in with Facebook.")) {
      Logger.log("Wrong username or password! Edit config.xml or delete the file to restart the setup agent!");
      System.exit(0);
    }
  }
}
