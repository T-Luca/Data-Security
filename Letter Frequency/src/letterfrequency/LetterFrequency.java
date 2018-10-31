package letterfrequency;

import java.util.*;
import java.io.*;

public class LetterFrequency 
{
    public static void main(String[] args) throws IOException 
    {
	//display the content from input.txt in console
        try (BufferedReader str = new BufferedReader(new FileReader("input.txt"))) 
        {
            for (String line; (line = str.readLine()) != null;) 
            {
                System.out.println("Input Data: " + line);
            }
        }
        
        //read the file
        File file = new File("input.txt");
        BufferedReader in_file = new BufferedReader(new FileReader(file));
        String output = in_file.readLine();
        output=output.toLowerCase();
        
        int length = output.length();
        char character;
        int totalCount = 0;

        Map<Character, Integer> map = new HashMap<Character, Integer>();
        
        for (int i = 0; i < length; i++)
        {
            character = output.charAt(i);
            //don't count spaces
            if(character != ' ')
            {
                totalCount++;  //total number of characters
                Integer countForCharacter = 0;
                //check in map if we have a count for this character
                if (map.containsKey(character)) 
                {
                    //get the current count we have for this character
                    countForCharacter = map.get(character);
                    //increment
                    countForCharacter++;
                } 
                else 
                {
                    countForCharacter = 1;
                }

                //put the up to date count into the map
                map.put(character, countForCharacter);
            }
        }
        //Get the found characters as an array of character
        Character[] charactersFound = map.keySet().toArray(new Character[0]);
        
        // print to output.txt
        PrintWriter outputfile = new PrintWriter("output.txt"); 
        outputfile.println("Letters\tCount\tFrequency");
        for(int k = 0; k < charactersFound.length; k++)
        {
            character = charactersFound[k];
            outputfile.println(character + 
                    "\t" +
                    //get the count for the character
                    map.get(character) +
                    "\t"+
                    //get the count for the character and divides by totalCount
                    ((map.get(character)/((float)totalCount))*100) + " %");
        }
        outputfile.close();	
        
        //display the content from output.txt in console
        try (BufferedReader str = new BufferedReader(new FileReader("output.txt"))) 
        {
            for (String line; (line = str.readLine()) != null;) 
            {
                System.out.println(line);
            }
        }     
    }
}