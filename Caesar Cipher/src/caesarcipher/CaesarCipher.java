package caesarcipher;

import java.io.*;

public class CaesarCipher
{

    public static void main(String[] args) throws IOException
    {   
        
        //display content from input.txt
        try (BufferedReader brs = new BufferedReader(new FileReader("input.txt"))) 
        {
            for (String line; (line = brs.readLine()) != null;) 
            {
                System.out.println("Input Data to encrypt:" + line);
            }
        }
	
        //read file
        File file=new File("input.txt");	
        FileReader ins=new FileReader(file);	
        BufferedReader br=new BufferedReader(ins);
		
        String str = br.readLine();
        br.close();
  
        //key value/shifting
        int key=3;
        
        //Encryption
        String encrypted = "";
        for(int i = 0; i < str.length(); i++)
        {
            int c = str.charAt(i);
            if (Character.isUpperCase(c))
            {		
                c = c + (key % 26);
                if (c > 'Z')
                c = c - 26;
            }
            else if (Character.isLowerCase(c))
            {
                c = c + (key % 26);
                if (c > 'z')
                c = c - 26;
            }
            encrypted += (char) c;
        }
        System.out.println("Encrypted:" + encrypted);
        
        File file1=new File("output.txt");
        FileWriter fw = new FileWriter(file1.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
                
        bw.write(encrypted);    
        bw.close();
		
        //Decryption
        String decrypted = "";
        for(int i = 0; i < encrypted.length(); i++)
        {
            int c = encrypted.charAt(i);
            if (Character.isUpperCase(c))
            {
                c = c - (key % 26);
                if (c < 'A')
                c = c + 26;
            }
            else if (Character.isLowerCase(c))
            {
                c = c - (key % 26);
                if (c < 'a')
                c = c + 26;
            }
            decrypted += (char) c;
        }
        System.out.println("Decrypted:" + decrypted);
    }	
}