// ["tar", "rat", "cat", "silent", "art", "listen"]
// [["tar", "rat", "art"], ["silent", "listen"], ["cat"]]

// HashSet<String> added

/*
empty array
array - everything is the same 
How are we handeling exceptions  - "A", 1 - throwing exceptions correclty 
Sorted input 
Class - mutability 
Large input 
*/

/*
 A whole bunch or processors running anagram solver - feed off of an SQS queue 
     Assuming multiple files - Each processor can handle one file
         Processor takes in a file, groups the anagrams, posts the processed anagrams to the rest endpoint (abstracted database), discard the file if processed correctly 
           
     Rest endpoint - key that identifies the anagrams, a list containing all the angrams from that processor that match my key 
     Database abstracted by that rest endpoint - this is where you would store the anagrams themselves 
     query the endpoint - give me all the angrams from the last hour 
     How long do we need to store anagrams 
     Highly avliable anagrams - sharded database - across avlability zones 
    
    Scale up processors shoudln't be hard 
    posting to the rest endpoint - database - sharding 
    
One big file - split is up - potentially a preprocessing step 
    preprocessed database - then the "Parallel" process above could feed divide 

*/


public List<List<String>> groupAnagrams(Strings[] words) {
    List<List<String>> result = new ArrayList<List<String>>(); 
    
    Map<String, List<Strings>> anagramGroups = new HashMap<String, List<String>>(); 
    for(String str : words) {
        int[] frequency = new int[26]; 
        for(int i = 0; i < str.length(); i++) {
            // a - 0 ascii 97?
            frequency[(int) (str.charAt(i) - 'a')] = frequency[(int) (str.charAt(i) - 'a')] + 1; 
        }
        String key = new String(frequency);
        
        if(anagramGroups.containsKey(key)) {
            anagramGroups.get(key).add(str); 
        } else {
            List<String> anagrams = new ArrayList<String>(); 
            anagrams.add(str); 
            anagramGroups.put(key, anagrams); 
        }
     }
     
     result.addAll(anagramGroups.values()); 
     
     return result; 
} 