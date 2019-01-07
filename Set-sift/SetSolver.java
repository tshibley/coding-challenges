import java.util.*; 

public class SetSolver {

	private static int numberOfCards;
	private static boolean printed;

	public static void main(String[] args) {
		ArrayList<Card> cards = readInput();
		ArrayList<HashSet<Card>> sets = getSets(cards); 
		System.out.println(); 
		System.out.println(sets.size()); 
		getLargestDisjoint(sets); 
	}

	private static ArrayList<Card> readInput() {
		ArrayList<Card> result = new ArrayList<Card>(); 
		Scanner s = new Scanner(System.in); 
		numberOfCards = s.nextInt(); 
		s.nextLine();
		if (numberOfCards < 3 || numberOfCards > 30) {
			throw new IllegalArgumentException("Invalid number of cards"); 
		}
		for (int i = 0; i < numberOfCards; i++) {
			String cardValue = s.nextLine(); 
			result.add(new Card(cardValue)); 
		}
		return result; 
	}

	private static ArrayList<HashSet<Card>> getSets(ArrayList<Card> cards) {
		ArrayList<HashSet<Card>> sets = new ArrayList<HashSet<Card>>();
		for(int i = 0; i < cards.size(); i++) {
			Card card1 = cards.get(i);
			for(int j = i + 1; j < cards.size(); j++) {
				Card card2 = cards.get(j);
				// Small optimization -  Given any two set cards, they have exactly 
				//	one other card in the deck that can form a set with them, so once 
				//	have found that set we can quit. 
				int k = j + 1; 
				boolean setFound = false; 
				while(!setFound && k < cards.size()) {
					Card card3 = cards.get(k);
					if (isSet(card1, card2, card3)) {
						HashSet<Card> set = new HashSet<Card>(); 
						set.add(card1); 
						set.add(card2); 
						set.add(card3); 
						sets.add(set);
						setFound = true; 
					}
					k++; 
				}
			}
		}
		return sets; 
	}

	private static void getLargestDisjoint(ArrayList<HashSet<Card>> sets) {
		// The largest possible disjoint collection of sets is the number of cards 
		// we have divded by three, so we can start there and work down until we find 
		// a collection of disjoint sets
		int searchSize = numberOfCards / 3; 
		printed = false;
		while(!printed) {
			getCombos(sets, new ArrayList<HashSet<Card>>(), 0, sets.size() - 1, 0, searchSize); 
			searchSize--; 
		}
	}

	private static void getCombos(ArrayList<HashSet<Card>> sets, ArrayList<HashSet<Card>> data, int start,
									 int end, int index, int n) {
		if (index == n && printed == false) {
			//Since we can print out any of the largest set, just print the first one and stop 
            System.out.println(data.size()); 
			System.out.println(); 
			for(HashSet<Card> set : data){
				for(Card card : set) {
					System.out.println(card.toString); 
				}
				System.out.println(); 
			}
			printed = true;
        } else if (printed == false) {
	        for (int i = start; i <= end && end - i + 1 >= n - index; i++) {
	        	if (isDisjoint(data, sets.get(i))) {
	        		ArrayList<HashSet<Card>> temp = new ArrayList<HashSet<Card>>(data);
	            	temp.add(sets.get(i));
	            	getCombos(sets, temp, i+1, end, index+1, n);
	            }
	        }
	    }
	}

	private static boolean isDisjoint(ArrayList<HashSet<Card>> sets, HashSet<Card> checkSet) {
		for(HashSet<Card> set : sets) {
			for(Card card : set) {
				for(Card checkCard : checkSet) {
					if (checkCard.equals(card)) {
						return false; 
					}
				}
			}
		}
		return true; 
	}

	private static boolean isSet(Card card1, Card card2, Card card3) {
		return (card1 != card2 && card2 != card3 && card1 != card3 && 
				allEqualOrNotEqual(card1.color, card2.color, card3.color) &&
				allEqualOrNotEqual(card1.type, card2.type, card3.type) &&
				allEqualOrNotEqual(card1.letter, card2.letter, card3.letter) &&
				((card1.count == card2.count && card2.count == card3.count && card1.count == card3.count) ||
				(card1.count != card2.count && card2.count != card3.count && card1.count != card3.count))); 
	}

	private static boolean allEqualOrNotEqual(String a, String b, String c) {
		return (a.equals(b) && b.equals(c) && a.equals(c)) || 
				(!a.equals(b) && !b.equals(c) && !a.equals(c)); 
	}

	/*
		Private inner class representing a single card from the game of set
	*/
	private static class Card {
		String color; 
		int count; 
		String type; 
		String letter;
		String toString; 

		public Card(String input) {
			String[] parsed = input.trim().split("\\s+");
			if(parsed.length != 2) {
				throw new IllegalArgumentException(input); 
			}
			color = parsed[0]; 
			if (!(color.equals("yellow") || color.equals("blue") || color.equals("green"))) {
				throw new IllegalArgumentException(input); 
			}
			char endDataChar = parsed[1].charAt(0); 

			this.setLetter(endDataChar, input); 
			this.setType(endDataChar, input);
			count = parsed[1].length(); 
			toString = input; 
		}

		public boolean equals(Card other) {
			return (this.toString.equals(other.toString)); 
		}

		private void setLetter(char endDataChar, String input) {
			if (equalsOne(endDataChar, 'S', 's', '$')) { 
				letter = "S"; 
			} else if (equalsOne(endDataChar, 'A', 'a', '@')) {
				letter = "A"; 
			} else if (equalsOne(endDataChar, 'H', 'h', '#')) {
				letter = "H"; 
			} else {
				throw new IllegalArgumentException(input);
			}
		}

		private void setType(char endDataChar, String input) {
			if (equalsOne(endDataChar, 'S', 'A', 'H')) { 
				type = "upper"; 
			} else if (equalsOne(endDataChar, 's', 'a', 'h')) {
				type = "lower"; 
			} else if (equalsOne(endDataChar, '@', '$', '#')) {
				type = "symbol"; 
			} else {
				throw new IllegalArgumentException(input);
			}
		}

		private boolean equalsOne(char val, char check1, char check2, char check3) {
			return (val == check1) || (val == check2) || (val == check3); 
		}
	}
}