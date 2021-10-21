package com.cloudflare.hellourld.utils;

public class Base62EncodeDecode {
    /*converts the input long to a base62 string and returns the string
    * */
    private static final String BASE62_RANGE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Long BASE = 62L;
    public static String convertIdtoShortUrl(Long id){
        StringBuilder sb = new StringBuilder();
        while(id >0){
            sb.append(BASE62_RANGE.charAt((int) (id%BASE)));
            id = id/BASE;
        }
        return sb.reverse().toString();
    }

    /*converts the input long to a base62 string and returns the string
     * */
    public static Long convertShortUrltoId(String shorturl){
        StringBuilder sb = new StringBuilder(shorturl);
        String rev_str = sb.reverse().toString();
        long base10Num = 0L;
        int digit = 0;
        for (int i = 0; i < rev_str.length(); i++){
            char c = rev_str.charAt(i);
            int indx = BASE62_RANGE.indexOf(c);
            base10Num += indx * Math.pow(BASE,digit);
            digit += 1;
        }
        return base10Num;
    }
}