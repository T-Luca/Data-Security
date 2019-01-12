package trifidcipher;

import java.util.ArrayList;
import java.util.HashMap;

public class TrifidCipher {

	private int keySize = 5;
	public static void main(String[] args) {

		TrifidCipher triMy = new TrifidCipher();
		System.out.println(triMy.encipher("ANAAREMERE", "ALEXNDRUBCFGHIJKMOPQSTVWYZ.", 5));
		System.out.println(triMy.decipher("AACQAXASUS", "ALEXNDRUBCFGHIJKMOPQSTVWYZ.", 5));

	}
	public String encipher(String userInput, String key) {
		return "";
	}
	public String decipher(String userInput, String key) {
		return "";
	}


	public String encipher(String userInput, String key, int keyS) {
		System.out.println("test");
		int keySize = keyS;

		char[][][] keySquareFirst = new char[3][3][3];
		for (int i = 0, y = 0, x = 0, index = 0; true; x++, index++) {
			if(x == 3){x = 0; y++;}
			if(y == 3){y = 0; i++;}
			if(i == 3){break;}
			keySquareFirst[i][y][x] = key.charAt(index);
		}

		ArrayList<Integer> listOneLetter = new ArrayList<>();
		HashMap<Integer, ArrayList<Integer>> mapAllText = new HashMap<>();
		for (int i = 0, y = 0, x = 0, index = 0; true; x++) {
			if(x == 3){x = 0; y++;}
			if(y == 3){y = 0; i++;}
			if(i == 3) {i = 0;}
			if(userInput.charAt(index) == keySquareFirst[i][y][x]){
				listOneLetter.add(i); listOneLetter.add(y);	listOneLetter.add(x);
				mapAllText.put(index, new ArrayList<>(listOneLetter));
				listOneLetter.clear();
				{i = 0; y = 0; x = -1; index++;}
				if(index == userInput.length()){break;}
			}
		}

		System.out.println(mapAllText);

		ArrayList<Integer> lastList = new ArrayList<>();
		int keyBuff = 0;
		int i = 0, j = 0;
		boolean testFirst = true;
		while (lastList.size() < userInput.length() * 3){
			lastList.add(mapAllText.get(i).get(j));
			i++;
			if((i - keyBuff) % keySize == 0){i -= keySize; j++;}
			if(j == 3){i += keySize; j = 0;}
			if ((mapAllText.size() % keySize) + (lastList.size() / 3) == userInput.length() && testFirst) {
				testFirst = false;
				keySize = mapAllText.size() % keySize;
				keyBuff = i;
			}
		}

		StringBuilder encipherText = new StringBuilder();
		for(int index = 0; index + 3 <= lastList.size(); index += 3){
			encipherText.append(keySquareFirst[lastList.get(index)][lastList.get(index + 1)][lastList.get(index + 2)]);
		}
		System.out.println(keySize + " " + keyBuff + " <--- from en");
		return encipherText.toString();
	}


	
	public String decipher(String userInput, String key, int keyS) {
		int keySize = keyS;

		char[][][] keySquareFirst = new char[3][3][3];
		for (int i = 0, y = 0, x = 0, index = 0; true; x++, index++) {
			if(x == 3){x = 0; y++;}
			if(y == 3){y = 0; i++;}
			if(i == 3){break;}
			keySquareFirst[i][y][x] = key.charAt(index);
		}

		ArrayList<Integer> listOneLetter = new ArrayList<>();
		HashMap<Integer, ArrayList<Integer>> mapAllText = new HashMap<>();
		for (int i = 0, y = 0, x = 0, index = 0; true; x++) {
			if(x == 3){x = 0; y++;}
			if(y == 3){y = 0; i++;}
			if(i == 3) {i = 0;}
			if(userInput.charAt(index) == keySquareFirst[i][y][x]){
				listOneLetter.add(i); listOneLetter.add(y);	listOneLetter.add(x);
				mapAllText.put(index, new ArrayList<>(listOneLetter));
				listOneLetter.clear();
				{i = 0; y = 0; x = -1; index++;}
				if(index == userInput.length()){break;}
			}
		}

		ArrayList<Integer> listSecond = new ArrayList<>();
		for (int i = 0; i < mapAllText.size(); i++){
            listSecond.add(mapAllText.get(i).get(0));
			listSecond.add(mapAllText.get(i).get(1));
			listSecond.add(mapAllText.get(i).get(2));
		}

		System.out.println(listSecond); 

		ArrayList<Integer> listOneLetterSecond = new ArrayList<>();
		StringBuilder decipherText = new StringBuilder();

		int keyBuff = 0;
		int i = 0, j = 0;
		boolean testFirst = true;
		while (listOneLetterSecond.size() < userInput.length() * 3){
			listOneLetterSecond.add(listSecond.get(i));
			listOneLetterSecond.add(listSecond.get(i + keySize));
			listOneLetterSecond.add(listSecond.get(i + 2 * keySize));
			decipherText.append(keySquareFirst[listSecond.get(i)][listSecond.get(i + keySize)][listSecond.get(i + 2 * keySize)]);

			i++;
			if((i - keyBuff) % keySize == 0){i += keySize * 2;}

			if ((mapAllText.size() % keySize) + (listOneLetterSecond.size() / 3) == userInput.length() && testFirst) {
				testFirst = false;
				keySize = mapAllText.size() % keySize;
				keyBuff = i;
			}
		}
		System.out.println(keySize + " " + keyBuff + " <--- from de");
		return decipherText.toString();
	}
}
