import java.util.*; 

public class PickServer {

	public static void main(String[] args) {
		ArrayList<String> vals = new ArrayList<String>(); 
		for(String arg : args) {
			String[] parts = arg.split(":"); 

			for(int i = 0; i < Integer.parseInt(parts[1]); i++) {
				vals.add(parts[0]); 
			}	
		}
		Random r = new Random();
		String random = vals.get(r.nextInt(vals.size()));
		System.out.println(random); 
	}
}