package vigenerecipher;

import java.io.*;
import java.util.*;

public class VigenereCipher
{
    public static void main(String[] args) throws IOException 
    {
	Scanner input = new Scanner(System.in);
	char c;
	String filename;
	String fileout;
	String key;
	System.out.print("Encrypt or decrypt a file (e/d): ");
	c = input.nextLine().charAt(0);
		
        //Interpret user input
        switch (c) {
            case 'e':
            case 'E':
                System.out.print("Please enter file to encrypt (ex: plaintext.txt): ");
                filename = input.nextLine();
                System.out.print("Enter key (ex: LEMON): ");
                key = input.nextLine();
                System.out.print("Enter output file (ex: ciphertext.txt): ");
                fileout = input.nextLine();
                encrypt(filename,key,fileout);
                break;
            case 'd':
            case 'D':
                System.out.print("Please enter file to decrypt (ex: ciphertext.txt): ");
                filename = input.nextLine();
                System.out.print("Enter key (ex: LEMON): ");
                key = input.nextLine();
                System.out.print("Enter output file (ex: plaintext.txt): ");
                fileout = input.nextLine();
                decrypt(filename,key,fileout);
                break;
            default:
                System.out.println("There's no such option !");
                System.exit(0);
        }	
    }
    
    //encrypt a file
    public static void encrypt(String filename, String keyphrase ,String fileout) throws IOException
    {
	char c;
	int counter = 0;
	BufferedReader in = new BufferedReader(new FileReader(filename));
	PrintWriter out = new PrintWriter(new FileWriter(fileout));
	while( ( c = (char)in.read() ) != (char)-1 )
	{
            //Convert all characters to upper case
            c = Character.toUpperCase(c);
				
            //Determine if the characters read are letters, also keeps new line chars.
            if( c <= 'Z' && c >= 'A' || c == '\r' || c == '\n' )
            {	
		if( c <= 'Z' && c >= 'A')
		{
                    //Shifts by the letters of the key
                    c = (char)( (c- 'A' + keyphrase.charAt(counter++)- 'A')%26 + 'A');
							
                    //Tiles the key
                    counter %= keyphrase.length();
		}
		out.print(c);
            }
        }
	//Close file I/O
	in.close();
	out.close();	
    }
    
    //decrypt a file
    public static void decrypt(String filename, String keyphrase ,String fileout) throws IOException
    {
        char c;
	int counter = 0;
	BufferedReader in = new BufferedReader(new FileReader(filename));
	PrintWriter out = new PrintWriter(new FileWriter(fileout));
		
        //Reads in the file
	while( ( c = (char)in.read() ) != (char)-1 )
	{
            if( c <= 'Z' && c >= 'A')
            {
		c = (char)( (c - keyphrase.charAt(counter++) + 26 )%26 + 'A');
		//Tiles the key
		counter %= keyphrase.length();
            }
            out.print(c);
	}
	in.close();
	out.close();
    }
}