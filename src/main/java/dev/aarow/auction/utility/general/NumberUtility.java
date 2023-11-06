package dev.aarow.auction.utility.general;

public class NumberUtility {

    public static boolean isInteger(String input){
        try{
            Integer.parseInt(input);
            return true;
        }catch(NumberFormatException exception){
            return false;
        }
    }
}
