package autokeycipher;

import java.io.*;

public class AutokeyCipher {
    public static void main(String args[]) throws IOException {

	// read input plaintext from file
	FileReader plaintextFile = new FileReader("plaintext.txt");
	BufferedReader readPlaintextFile = new BufferedReader(plaintextFile);
	String plaintext = readPlaintextFile.readLine();
	readPlaintextFile.close();

	// take input Key
	String keyText = "KILT";

	// append key and plaintext to match number of characters in both
	String key = keyText + plaintext;
	int match = key.length() - keyText.length();
	key = key.substring(0, match);

	if (isAlpha(plaintext)) {
                String ciphertext = encrypt(key, plaintext);
                System.out.println("Ciphertext: " + ciphertext);
                plaintext = decrypt(key, ciphertext);
                System.out.println("Plaintext:  " + plaintext);
        } else {
                System.out.println(plaintext + " \nAll the characters should be Alphabets");
	}
    }

    public static boolean isAlpha(String plaintext) {
	return plaintext.matches("[a-zA-Z]+");
    }

    private static String encrypt(String key, String plaintext) throws IOException {
	key = key.toUpperCase();
	plaintext = plaintext.toUpperCase();
	System.out.println("Plaintext:  " + plaintext);
	System.out.println("Autokey:    " + key);
	String setOfAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String ciphertext = "";
	for (int i = 0; i < plaintext.length(); i++) {
		int cipher = (key.charAt(i) + plaintext.charAt(i)) % 26;
		ciphertext = ciphertext + setOfAlphabets.charAt(cipher);
	}
	FileWriter writeCipher = new FileWriter("ciphertext.txt");
	BufferedWriter c = new BufferedWriter(writeCipher);
	c.write(ciphertext);
	c.close();
	return ciphertext;
    }

    private static String decrypt(String key, String ciphertext) throws IOException {
	key = key.toUpperCase();
	String setOfAlphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	String plainText = "";
	for (int i = 0; i < ciphertext.length(); i++) {
		int text = (ciphertext.charAt(i) - key.charAt(i)) % 26;
		if (text < 0)
			text = text + 26;

		plainText = plainText + setOfAlphabets.charAt(text);
	}
	FileWriter writePlainText = new FileWriter("plaintext.txt");
	BufferedWriter c = new BufferedWriter(writePlainText);
	c.write(plainText);
	c.close();
	return plainText;
    }
}
