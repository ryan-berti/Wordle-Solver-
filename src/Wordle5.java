public class Wordle5 {
	
	public static void main(String[] args) {

		System.out.println("Welcome to the Wordle Simulator!");
		System.out.println();
	

		// Green characters (ie known character, known position)
		// Format: [alphabet: 0-25][position: 0-4]
		System.out.print("No. of green blocks: ");
		int howManyGreens = StdIn.readInt();
		System.out.println();
		int[][] greens = new int[howManyGreens][2];

		// Green inputs
		System.out.println("Input Green block details in the form: character,position");
		for (int i = 0; i < howManyGreens; i++) {
			System.out.print("Green #" + (i+1) + ": ");
			String temp = StdIn.readString();
			greens[i][0] = charToPosition(temp.charAt(0));
			greens[i][1] = Character.getNumericValue(temp.charAt(2)) - 1;
		}

		
		// Yellow characters (ie known character, unknown position)
		// Yellow characters from previous rows must be entered
		// Format: [alphabet: 0-25][position: 0-4]
		System.out.println();
		System.out.print("No. of yellow blocks: ");
		int howManyYellows = StdIn.readInt();
		System.out.println();
		int[][] yellows = new int[howManyYellows][2];

		// Yellow inputs
		System.out.println("Input Yellow block details in the form: character,position");
		for (int i = 0; i < howManyYellows; i++) {
			System.out.print("Yellow #" + (i+1) + ": ");
			String temp = StdIn.readString();
			yellows[i][0] = charToPosition(temp.charAt(0));
			yellows[i][1] = Character.getNumericValue(temp.charAt(2)) - 1;
		}

		// White inputs
		System.out.println();
		System.out.print("No. of white blocks: ");
		int howManyWhites = StdIn.readInt();
		System.out.println();
		int[] whites = new int[howManyWhites];

		for (int i = 0; i < howManyWhites; i++) {
			System.out.print("White #" + (i+1) + ": ");
			whites[i] = charToPosition(StdIn.readString().charAt(0));
		}

		// Grey inputs
		System.out.println();
		System.out.print("No. of grey blocks: ");
		int howManygreys = StdIn.readInt();
		System.out.println();
		int[] greys = new int[howManygreys];

		for (int i = 0; i < howManygreys; i++) {
			System.out.print("Grey #" + (i+1) + ": ");
			greys[i] = charToPosition(StdIn.readString().charAt(0));
		}

		// Run Simulation and print out results
		String[] a = simulator(greens, yellows, whites, greys);

		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i] + "			" + (i+1));
		}

	}
	
	public static String[] simulator(int[][] greens, int[][] yellows, int[] whites, int[] greys) {
		
		// a[i][j]=  true; implies that i-th letter of alphabet is ellegable for j-th block position
		boolean[][] booleanCombinationGrid = new boolean[26][5];

		// Initiate a value of true for all elements
		for (int i = 0; i < 26; i++) {
			for (int j = 0; j < 5; j++) {
				booleanCombinationGrid[i][j] = true;
			}
		}

		// Correct for grey input
		for (int a = 0; a < greys.length; a++) {
			for (int j = 0; j < 5; j++) {
				booleanCombinationGrid[greys[a]][j] = false;
			}
		}

		// correct for yellow input
		for (int a = 0; a < yellows.length; a++) {
			booleanCombinationGrid[yellows[a][0]][yellows[a][1]] = false;
		}

		// Correct for green input
		for (int a = 0; a < greens.length; a++) {

			// Make every block in green column equal false
			for (int i = 0; i < 26; i++) {
				booleanCombinationGrid[i][greens[a][1]] = false;
			}

			// Make only one character in green column equal to true
			booleanCombinationGrid[greens[a][0]][greens[a][1]] = true;
		}

		// Count no. of possibilities for each block and store in a 1D array called prelimVector
		int[] prelimVector = new int[5];

		for (int j = 0; j < 5; j++) {
			for (int i = 0; i < 26; i++) {
				if (booleanCombinationGrid[i][j]) prelimVector[j]++;
			}
		}

		// Create 5 1D integer arrays containing all possible letters for each block;
		int[] block1 = new int[prelimVector[0]];
		int[] block2 = new int[prelimVector[1]];
		int[] block3 = new int[prelimVector[2]];
		int[] block4 = new int[prelimVector[3]];
		int[] block5 = new int[prelimVector[4]];

		// Fill each block array
		int count = 0;
		for (int i = 0; i < 26; i++) {
			if (booleanCombinationGrid[i][0]) {
				block1[count] = i;
				count++;
			}
		}

		count = 0;
		for (int i = 0; i < 26; i++) {
			if (booleanCombinationGrid[i][1]) {
				block2[count] = i;
				count++;
			}
		}

		count = 0;
		for (int i = 0; i < 26; i++) {
			if (booleanCombinationGrid[i][2]) {
				block3[count] = i;
				count++;
			}
		}

		count = 0;
		for (int i = 0; i < 26; i++) {
			if (booleanCombinationGrid[i][3]) {
				block4[count] = i;
				count++;
			}
		}

		count = 0;
		for (int i = 0; i < 26; i++) {
			if (booleanCombinationGrid[i][4]) {
				block5[count] = i;
				count++;
			}
		}

		// Calculate how many preliminary combinations exist (Preliminary = not taking into account all yellow implications)
		// Store all preliminary combinations in a 2D integer array.
		int prelimPossibilities = prelimVector[0] * prelimVector[1] * prelimVector[2] * prelimVector[3] * prelimVector[4];
		int[][] prelimCombinations = new int[prelimPossibilities][5];
		count = 0;

		for (int a = 0; a < block1.length; a++) {
			for (int b = 0; b < block2.length; b++) {
				for (int c = 0; c < block3.length; c++) {
					for (int d = 0; d < block4.length; d++) {
						for (int e = 0; e < block5.length; e++) {

							prelimCombinations[count][0] = block1[a];
							prelimCombinations[count][1] = block2[b];
							prelimCombinations[count][2] = block3[c];
							prelimCombinations[count][3] = block4[d];
							prelimCombinations[count][4] = block5[e];
							count++;
						}
					}
				}
			}
		}

		// Remove combinations that do not contain any yellow letters
		for (int i = 0; i < prelimPossibilities; i++) {
			boolean[] hasYellow = new boolean[yellows.length];
			for (int j = 0; j < yellows.length; j++) {
				for (int k = 0; k < 5; k++) {
					if (yellows[j][0] == prelimCombinations[i][k]) {
						hasYellow[j] = true;
					}
				}
			}
			// Set first block value = -1 if combination is not feasible
			for (int j = 0; j < yellows.length; j++) {
				if (!hasYellow[j]) {
					prelimCombinations[i][0] = -1;
				}
			}
		}

		// Count final no. of feasible combinations
		int finalPossibilities = 0;

		for (int i = 0; i < prelimPossibilities; i++) {
			if (prelimCombinations[i][0] != -1 ) {
				finalPossibilities++;
			}
		}

		// Print out final no. of combinations
		System.out.println();
		System.out.println("No. of combinations: " + finalPossibilities);
		System.out.println();

		// Create final 2D array of all feasible combinations
		// Fill array with all elements from prelimCombinations, whose 1st element is NOT -1
		int[][] finalCombinations = new int[finalPossibilities][5];
		count = 0;

		for (int i = 0; i < prelimPossibilities; i++) {
			if (prelimCombinations[i][0] != -1 ) {
				for (int j = 0; j < 5; j++) {
					finalCombinations[count][j] = prelimCombinations[i][j];
				}
				count++;
			}
		}

		// Convert finalCombinations (2D array) to 1D array of Strings
		String[] wordleChoices = new String[finalPossibilities];

		for (int i = 0; i < finalPossibilities; i++) {
			wordleChoices[i] = positionToChar(finalCombinations[i]);
		}

		return wordleChoices;

	}

	// Method converts an integer array (positions in alphabet) to corrosponding string of characters
	public static String positionToChar(int[] positions) {
		
		String answer = "";
		char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

		for (int i = 0; i < positions.length; i++) {
			answer += Character.toString(alphabet[positions[i]]);
		}

		return answer;
	}

	// Method converts a character to its integer position in the alphabet
	public static int charToPosition(char a) {

		char[] alphabet = {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};

		for (int i = 0; i < alphabet.length; i++) {
			if (a == alphabet[i]) {
				return i;
			}
		}

		return -1;
	}
}
