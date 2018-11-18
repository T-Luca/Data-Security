package playfaircipher;

import java.util.Scanner;
import java.io.*;

public class PlayfairCipher
{
    //Matrix is always 5X5
    static char[][] key = new char[5][5];
    
    public static void main(String[] args) throws FileNotFoundException, IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the keyword: ");
        String k = sc.nextLine();
        k = k.toUpperCase();        //Capitalize the key
        k = removeDuplicates(k);    //Removes duplicate letters
        createMatrix(k);            //Creates key matrix
        
        //read file
        File file=new File("plaintext.txt");	
        FileReader ins=new FileReader(file);	
        BufferedReader br=new BufferedReader(ins);	
        String msg = br.readLine();
        br.close();
        
        msg = msg.toUpperCase();    //Capitalize the message
        msg = duplicateLetter(msg); //Removes duplicate letters
        if( msg.length()%2 != 0 )   //If pair is not formed then X is
            msg += "X";             //appended at the end of message
        String encryptedMsg = encryptMsg(msg);  //Message is encrypted
        String decryptedMsg = decryptMsg(encryptedMsg); //Message is decrypted
        System.out.println("Key matrix for the keyword: ");
        displayKeyMatrix();         //Displays key matrix
        
        //displays content from plaintext.txt
        try (BufferedReader brs = new BufferedReader(new FileReader("plaintext.txt"))) 
        {
            for (String line; (line = brs.readLine()) != null;) 
            {
                System.out.println("Input Data to encrypt: " + line);
            }
        }
        
        //2 bits group
        for(int i=0 ; i<msg.length()-1 ; i+=2 )
            System.out.print(msg.substring(i,i+2)+"\t");
        System.out.println();
        System.out.println("Encrypted Message: ");
        //Encrypted message is displayed
        for(int i=0 ; i<encryptedMsg.length()-1 ; i+=2 )
            System.out.print(encryptedMsg.substring(i,i+2)+"\t");
        System.out.println();
        
        //write file
        File file1=new File("ciphertext.txt");
        FileWriter fw = new FileWriter(file1.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);                
        bw.write(encryptedMsg);    
        bw.close();
       
        //Decrypted message is displayed
        System.out.println("Decrypted Message: " + decryptedMsg);
    }
    
    public static String removeDuplicates(String k)
    {
        String result="";
        for(int i=0 ; i<k.length() ; i++ )
        {
            if( k.charAt(i) == ' ' ) //Removes blank spaces
                continue;
            if( result.indexOf(k.charAt(i)) == -1 ) 
                result += k.charAt(i);
        }
        return result;
    }
    
    public static void createMatrix(String k)
    {
        int m=0,index,n=0;
        // J is not included for key matrix
        // variable chars is used for key matrix generation
        String chars = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        String tmp;
        for(int i=0 ; i< 5 ; i++ )
        {
            for(int j=0 ; j<5 ; j++ )
            {
                if ( m < k.length() )
                { //Key is stored in the matrix first
                    key[i][j] = k.charAt(m);
                    index = chars.indexOf(key[i][j]);
                    tmp = chars.substring( index+1,chars.length() );
                    chars = chars.substring(0, index) + tmp;
                    m++;
                }
                else
                {      //Then remaining alphabets are stored
                    key[i][j] = chars.charAt(n);
                    n++;
                }
            }
        }
    }
    
    public static void displayKeyMatrix()
    {
        for(int i=0 ; i<5 ; i++ )
        {
            for(int j=0 ; j<5 ; j++ )
                System.out.print(key[i][j]+"\t");
            System.out.println();
        }
    }
    
    //This method inserts X in between 2 duplicate letters
    public static String duplicateLetter(String msg)
    {
        String tmp;
        for(int i=0 ; i<msg.length()-1 ; i++ )
        {
            if( msg.charAt(i) == ' ' )
            {
                tmp = msg.substring( i+1,msg.length() );
                msg = msg.substring(0, i)  + tmp;
            }
            else if( msg.charAt(i) == msg.charAt(i+1) )
            {
                tmp = msg.substring( i+1,msg.length() );
                msg = msg.substring(0, i+1) + "X" + tmp;
            }
        }
        return msg;
    }
    
    public static String encryptMsg(String msg)
    {
        int[] a = new int[2];
        int[] b = new int[2];
        String encrypt="";
        for(int i=0; i<msg.length() ; i+=2 )
        {
            a = indexOfChar(msg.charAt(i));
            b = indexOfChar(msg.charAt(i+1));
            //If pair of letters are in same column
            if( a[1] == b[1] )
            {
                encrypt += key[(a[0]+1)%5][a[1]];
                encrypt += key[(b[0]+1)%5][b[1]];
            }
            //If pair of letters are in same row
            else if( a[0] == b[0] )
            {
                encrypt += key[a[0]][(a[1]+1)%5];
                encrypt += key[b[0]][(b[1]+1)%5];
            }
            //If pair of letters forms rectangle
            else
            {
                encrypt += key[a[0]][b[1]];
                encrypt += key[b[0]][a[1]];
            }
        }
        return encrypt;
    }
    //Returns the index i,j of character in key matrix
    public static int[] indexOfChar(char c)
    {
        int[] a = new int[2];
        for(int i=0 ; i<5 ; i++ )
            for(int j=0 ; j<5 ; j++ )
                if( key[i][j] == c )
                {
                    a[0] = i ;
                    a[1] = j ;
                    return a;
                }
        return a;
    }
    
    public static String decryptMsg(String msg)
    {
        int[] a = new int[2];
        int[] b = new int[2];
        String decrypt="";
        for(int i=0; i<msg.length() ; i+=2 )
        {
            a = indexOfChar(msg.charAt(i));
            b = indexOfChar(msg.charAt(i+1));
            //If pair of letters are in same column
            if( a[1] == b[1] )
            {
                decrypt += key[(a[0]+4)%5][a[1]];
                decrypt += key[(b[0]+4)%5][b[1]];
            }
            //If pair of letters are in same row
            else if( a[0] == b[0] )
            {
                decrypt += key[a[0]][(a[1]+4)%5];
                decrypt += key[b[0]][(b[1]+4)%5];
            }
            //If pair of letters forms rectangle
            else
            {
                decrypt += key[a[0]][b[1]];
                decrypt += key[b[0]][a[1]];
            }
        }
        int index;
        String tmp;
        int len = decrypt.length();
        //remove X 
        if ( decrypt.charAt(len-1) == 'X' )
            decrypt = decrypt.substring(0, len-1);
        for(int i=0 ; i<decrypt.length() ; i++ )
        {
            index = decrypt.indexOf("X");
            if( index < 0 ) //if there is no X in encrypted message
                break;
            else if( decrypt.charAt(index-1) == decrypt.charAt(index+1) )
            {
                tmp = decrypt.substring( index+1,decrypt.length() );
                decrypt = decrypt.substring(0, index)  + tmp;
            }
        }
        return decrypt;
    }  
}