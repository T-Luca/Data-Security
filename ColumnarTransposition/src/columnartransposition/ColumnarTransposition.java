package columnartransposition;

import java.io.*;

public class ColumnarTransposition 
{    
    private int[] numKey;
    
    private void numKey(String key, int enc)
    {
        numKey = new int[key.length()];
        String key1 = key;
        int k = 0;
        while(key1.length() > 0)
        {
            char sm = key1.charAt(0);
            for(int i =1; i<key1.length(); i++)
            {
                if(sm > key1.charAt(i))
                {
                sm = key1.charAt(i);
                }
            }
            for(int j = 0; j<key.length(); j++)
            {
                if(key.charAt(j) == sm)
                {
                    if(enc == 1)
                    {
                        numKey[k] = j;
                    }
                    else
                    {
                        numKey[j] = k;
                    }
                    k++;
                    key1 = key1.substring(0, key1.indexOf(key.charAt(j))) + key1.substring(key1.indexOf(key.charAt(j))+1);
                }
            }
        }
    }

    public String encrypt(String s, String key) throws IOException
    {
        String cipText = "";
        int rows = s.length()/key.length();
        if(s.length()%key.length() != 0)
        {
            rows++;
        }
        String[] CTC = new String[rows];
        int rem = key.length() - s.length()%key.length();
        numKey(key, 1);
        for(int i = 0; i<rows-1; i++)
        {
            CTC[i] = s.substring(i*key.length(), (i+1)*key.length());
        }
        CTC[rows-1] = s.substring((rows-1)*key.length(), s.length());
        for(int i = 0; i<rem; i++)
        {
            CTC[rows-1] = CTC[rows-1] + ' ';
        }

        for(int i = 0; i<key.length(); i++)
        {
            for(int j = 0; j<rows; j++)
            {
                cipText = cipText + CTC[j].charAt(numKey[i]);
            }
        }
        FileWriter writeCipher = new FileWriter("ciphertext.txt");
		BufferedWriter c = new BufferedWriter(writeCipher);
		c.write(cipText);
		c.close();
        return cipText;
    }

    public String decrypt(String s, String key)
    {
        String orgText = "";
        int rows = s.length()/key.length();
        if(s.length()%key.length() != 0)
        {
            rows++;
        }
        String[] CTC = new String[key.length()];
        numKey(key, 0);
        for(int i = 0; i<key.length(); i++)
        {
            CTC[i] = s.substring(rows*numKey[i], rows*(numKey[i]+1));
        }
        for(int i = 0; i<rows; i++)
        {
            for(int j = 0; j<key.length(); j++)
            {
                orgText = orgText + CTC[j].charAt(i);
            }
        }
        //orgText = orgText.replace('&',' ');
       
        return orgText;
    }

    public static void main(String[] args) throws IOException{
        
        ColumnarTransposition CC = new ColumnarTransposition();
        System.out.print("Columnar Transposition Cipher  \n");
        
        FileReader plaintextFile = new FileReader("plaintext.txt");
		BufferedReader input = new BufferedReader(plaintextFile);
		String plaintext = input.readLine();
		input.close();
			
		String inKey = "ADRIAN";
        
        String ct = CC.encrypt(plaintext, inKey);
        System.out.println("Encrypted text:  " + ct);
        System.out.println("Decryption of the Encrypted text: " + CC.decrypt(ct, inKey));
    }  
}
