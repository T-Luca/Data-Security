package beaufortcipher;

import java.io.*;

public class BeaufortCipher {

    public static void main(String[] args) throws IOException
    {
        String key="fortification";
        
        // read input plaintext from file
        FileReader plaintextFile = new FileReader("plaintext.txt");
	BufferedReader readPlaintextFile = new BufferedReader(plaintextFile);
	String phrase = readPlaintextFile.readLine();
	readPlaintextFile.close();
        
        String enc=encrypt(phrase, key);
        System.out.println("Beaufort Cipher");
        System.out.println("Ciphertext: " + enc);
        System.out.println("Plaintext:  " + decrypt(enc, key));
    }
    
    public static String encrypt(String p, String c) throws IOException
    {
        String phrase=p.toUpperCase();
        String key=c.toUpperCase();
        String keyPhrase="";
        String encryption="";
        phrase=phrase.replaceAll("\\s+","");
        for(int x=0; x<phrase.length(); x+=key.length())
        {
            keyPhrase+=key;
        }
        for(int x=0; x<phrase.length(); x++)
        {		
            int shift=keyPhrase.charAt(x)-phrase.charAt(x);
            int cypher;
            if(shift>=0)
                cypher=65+shift;
            else
                cypher=91+shift;
            String shiftedChar=Character.toString((char)cypher);
            encryption+=shiftedChar;
        }
        FileWriter writeCipher = new FileWriter("ciphertext.txt");
	BufferedWriter s = new BufferedWriter(writeCipher);
	s.write(encryption);
	s.close();
        return encryption;
    }
	
    public static String decrypt(String p, String c)
    {
        String phrase=p.toUpperCase();
        String key=c.toUpperCase();
        String keyPhrase="";
        String decryption="";
        phrase=phrase.replaceAll("\\s+","");
        for(int x=0; x<phrase.length(); x+=key.length())
        {
            keyPhrase+=key;
        }
        for(int x=0; x<phrase.length(); x++)
        {
            int shift=keyPhrase.charAt(x)-phrase.charAt(x);
            int shifted;
            if(shift>=0)
            {
            	shifted=65+shift;
            }
            else
            {
              	shifted=91+shift;
            }
            String shiftedChar=Character.toString((char)shifted);
            decryption+=shiftedChar;
        }
        return decryption;
    }
}
