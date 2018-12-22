package bifidcipher;

public class BifidCipher {
    
    private static final int COLANDROWLENGTH = 5;

    public static void encrypt(String message, String key, int keyCols) {
        String encryptedIntSequence = "";
        String encrypted = "";
        String alphKey = generateAlph(key);
        int messageLength = message.length();
        int[] rowCharId = new int[messageLength];
        int[] colCharId = new int[messageLength];

        for(int i = 0; i < messageLength; ++i) {
            rowCharId[i] = alphKey.indexOf(message.charAt(i)) / COLANDROWLENGTH; 
            colCharId[i] = alphKey.indexOf(message.charAt(i)) % COLANDROWLENGTH;
        }
        
        encryptedIntSequence = createIntSequence(rowCharId, colCharId, keyCols);
        
        for(int i = 0; i < encryptedIntSequence.length(); i += 2)
            encrypted += alphKey.charAt(Integer.parseInt("" + encryptedIntSequence.charAt(i)) * COLANDROWLENGTH +
                                        Integer.parseInt("" + encryptedIntSequence.charAt(i + 1)) % COLANDROWLENGTH);
        System.out.println(encrypted);
    }


    private static String generateAlph(String key) {
        String newAlph = "";
        String normalAlph = "abcdefghiklmnopqrstuvwxyz";
        key = key.replace('j', 'i');
        newAlph = addCharsToString(key, newAlph);
        newAlph = addCharsToString(normalAlph, newAlph);
        return newAlph;
    }

    private static String addCharsToString(String key, String str) {
        for(int i = 0; i < key.length(); ++i)
            if (str.indexOf(key.charAt(i)) == -1)
                str += key.charAt(i);
        return str;
    }

    private static String createIntSequence(int[] rowCharId, int[] colCharId, int keyCols) {
        String encryptedSequence = "";
        int addedAmount = 0;
        for(int i = 0; i < rowCharId.length; ++i) {
            encryptedSequence += rowCharId[i];
            addedAmount++;
            if ((i + 1) % keyCols == 0 || (i + 1) == rowCharId.length) {
                for(int j = i - addedAmount + 1; j <= i; ++j)
                    encryptedSequence += colCharId[j];
                addedAmount = 0;
            }
        }
        return encryptedSequence;
    }

    public static void decrypt(String message, String key, int keyCols) {
        String encryptedIntSequence = "";
        String decrypted = "";
        String alphKey = generateAlph(key);
        int messageLength = message.length();
        int[] rowCharId = new int[messageLength];
        int[] colCharId = new int[messageLength];

        for(int i = 0; i < messageLength; i += 1)
            encryptedIntSequence += "" + alphKey.indexOf(message.charAt(i)) / COLANDROWLENGTH + 
                                        alphKey.indexOf(message.charAt(i)) % COLANDROWLENGTH;

        decrypted = decryptedString(encryptedIntSequence, keyCols, alphKey);

        System.out.println(decrypted);
    }

    private static String decryptedString(String encryptedIntSequence, int keyCols, String alphKey) {
        String decrypted = "";
        int encryptedIntSequenceLength = encryptedIntSequence.length();
        for(int i = keyCols; i < encryptedIntSequenceLength; ++i) {
            decrypted += alphKey.charAt(Integer.parseInt("" + encryptedIntSequence.charAt(i - keyCols)) * COLANDROWLENGTH +
                                        Integer.parseInt("" + encryptedIntSequence.charAt(i)));
            if (i%keyCols == 4 && i + keyCols*2 < encryptedIntSequenceLength)
                i += keyCols;
            else if(i%keyCols == 4) {
                keyCols = (encryptedIntSequenceLength - i) / 2;
                i += keyCols;
            }
        }
        return decrypted;
    }

    public static void main(String[] args) {
        String key = "phqgmeaylnofdxkrcvszwbuti";
        String message = "defend the east wall of the castle";
        message = message.replace(" ", "");
        message = message.replace("j", "i").toLowerCase();
        
        System.out.println("Encrypted text:");
        encrypt(message, key, 5);
        
        System.out.println("Decryption of the Encrypted text:");
        decrypt("ffyhmkhycpliashadtrlhcchlblr", key, 5);
    }   
}
