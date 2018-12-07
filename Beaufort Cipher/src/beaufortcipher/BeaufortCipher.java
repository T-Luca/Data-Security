package beaufortcipher;

public class BeaufortCipher {

    public static void main(String[] args)
    {
        String key="fortification";
        String phrase="defend the east wall of the castle";
        String enc=encrypt(phrase, key);
        System.out.println("Beaufort Cipher");
        System.out.println("Ciphertext: " + enc);
        System.out.println("Plaintext:  " + decrypt(enc, key));
    }
    
    public static String encrypt(String p, String c)
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
