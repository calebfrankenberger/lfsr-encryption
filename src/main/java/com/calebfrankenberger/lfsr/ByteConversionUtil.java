package com.calebfrankenberger.lfsr;

public class ByteConversionUtil {

    final static String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

    // Convert a string of readable text into a string of 1s and 0s
    public static String convertPlainTextToBits(String string) {
        StringBuilder binary = new StringBuilder();
        int length = string.length();

        for(int i = 0; i < length; i++) {
            StringBuilder binaryString = getStringBuilder(string, i);

            int parsed = Integer.parseInt(binaryString.toString(), 2);
            String conv = String.valueOf(allowedCharacters.charAt(parsed));
            binary.append(binaryString);
        }

        return binary.toString();
    }

    private static StringBuilder getStringBuilder(String string, int i) {
        int character = string.charAt(i);
        int characterIndex = allowedCharacters.indexOf(character);

        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(characterIndex));
        int eachStringLen = binaryString.length();

        // If the binary string is less than 6, add a 0 to the leftmost side
        for(int j = 6; j > eachStringLen; j--) {
            binaryString.insert(0, "0");
        }
        return binaryString;
    }

    // Convert a string of 1s and 0s back into a readable string
    public static String convertBitsToPlainText(String string) {
        StringBuilder output = new StringBuilder();
        int numberOfCharacters = string.length()/6; // This is always going to be divisible by 6

        for(int i = 0; i < numberOfCharacters; i++) {
            String subString = string.substring(0, 6);
            int parsed = Integer.parseInt(subString, 2);
            String conv = String.valueOf(allowedCharacters.charAt(parsed));
            output.append(conv);
            string = string.substring(6);
        }

        return output.toString();
    }

}
