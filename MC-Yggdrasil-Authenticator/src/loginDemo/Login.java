package loginDemo;

import java.util.Scanner;
import authentication.AuthRequest;

/**
 * A text based demo client interface for authentication. Logs a user in to obtain a token between 
 * the client and the Minecraft authentication server and then logs the user out; this invalidates the token.
 * 
 * @author wpenson
 *
 */
public class Login {
	public static void main(String[] args) {
		System.out.println("Welcome to MCTextualClient! Please enter your Minecraft credentials.");
		System.out.print("Username: ");
		
		Scanner scan = new Scanner(System.in);
		String user = scan.nextLine();
		
		System.out.print("Password: ");
		
		String pass = scan.nextLine();
		
		AuthRequest auth = new AuthRequest(user, pass);
		
		if (auth.authenticate()) {
			System.out.println("Login successful! Access token: " + auth.getUser().getAccessToken());
		}
		else {
			System.out.println("Login failed!");
			System.exit(0);
		}
		
		scan.close();
	}
}
