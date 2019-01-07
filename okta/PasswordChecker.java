import java.util.*; 
import java.io.*; 

/**
 * A Class for checking the validity of passwords
 */
public class PasswordChecker {

	/**
	 * The main method takes in the name of a file containing passwords to 
	 * validate, and prints out the results of validating to System.out
	 */
	public static void main(String[] args) throws FileNotFoundException {
		if(args.length != 1) {
			throw new IllegalArgumentException("PasswordChecker takes in the name of the file"
				+ " containing your passwords to check as a string"); 
		}
		String inputFile = args[0]; 
		Scanner input = new Scanner(new File(inputFile)); 

		while(input.hasNextLine()) {
			String password = input.nextLine(); 
			if(!password.equals("end")) {
				if(isValidPassword(password)) {
					System.out.println("<" + password + ">" + " is acceptable."); 
				} else {
					System.out.println("<" + password + ">" + " is not acceptable.");
				}
			}
		}

		/* Code for testing speed 

		long startTime = System.nanoTime();
		for(int i = 0; i < 1000000; i++) {
			isValidPassword("mamamamajklamamamama"); 
		}
		long endTime = System.nanoTime();

		long duration = (endTime - startTime);
		System.out.println("Total time = " + duration); 

		*/
	}

	
	/**
	 * A static method for checking if a string is a valid password 
	 * 
	 * @param password
	 * 			The password to validate, as a String
	 * @requires password != null
	 * @return  A boolean representing whether or not the password is valid
	 */
	public static boolean isValidPassword(String password) {
		boolean valid = true; 
		boolean vowelFound = false; 
		int vowels = 0; 
		int consonants = 0; 
		for(int i = 0; i < password.length(); i++) {
			if(password.substring(i, i + 1).matches("[aeiou]")) {
				vowelFound = true; 
				vowels++; 
				consonants = 0; 
			} else {
				consonants++; 
				vowels = 0; 
			}
			if(i > 0 && password.charAt(i-1) == password.charAt(i)) {
				valid &= (password.charAt(i) == 'e' || password.charAt(i) == 'o'); 
			}
			valid &= (vowels < 3 && consonants < 3); 
		}
		return valid && vowelFound; 
	}
}

/**
 *  Notes on my solution, and the choices that I made: 
 *
 *  When I first read the question my immediate thought was a regex expression, however, 
 *  as I started working down that avenue I realized that the expression would be complex, 
 *  potentially difficult to maintain, and difficult to change if you wanted to update your password rules
 *  the future. The biggest  issue with that choice from my perspective though is that I wanted to make my isValidPassword 
 *  method a self-contained, static method, to make it super easy to use in other places. And if 
 *  I was using a regex, or a combination of regex expressions, I would have to compile them each time 
 *  it was called, which would most likely be slower than just looping through the string, or I would need 
 *  a client of this method to know what the password pattern is, which means it is no longer self contained. If I needed 
 *  performance above all else, and I was only using this method in one place, it might make more sense 
 *  to use a regex and pass the compiled version into the is valid password method, but I would want to 
 *  do real world performance testing to be sure. 
 *  
 *  I did want to make sure that this solution was still relativly fast, so I did a speed test on one million
 *  20 character passwords of just the isValidPassword method (since file io/printing is fairly slow, and in 
 *  a real application we wouldn't be using plain text files to store passwords ;p ). Over repeated tests it 
 *  ran in about 4.2 seconds, which seems like decent performance for most situations. Obviously with a really 
 *  high load, I'd want to test other solutions to make sure this is the best choice. 
 */
