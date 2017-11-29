//package temp;
//
//import com.sun.deploy.net.URLEncoder;
//
//import java.io.UnsupportedEncodingException;
//
///**
// * Created by kerno on 1/23/2016.
// */
//public class YHEntry {
//    private  YHEntry(){}
//    public static YHEntry entry = new YHEntry();
//    public String entry(String plainText) {
////        String plainText="001004001010101";
////         plainText="010100074075";
//        String encText="";
////        plainText = jsencode(plainText);
//        if(plainText!="")
//        {
//            for(int i = 0; i < plainText.length(); i++)
//            {
//                encText += plainText.charAt(i) - 23;
//            }
//            encText = "enc||"+ jsencode(encText);
//        }
//        try {
//            return URLEncoder.encode(encText,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//    private String jsencode(String input){
//        String _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";;
//        String output = "";
//        char chr1, chr2, chr3;
//                int enc1, enc2, enc3, enc4;
//        int i = 0;
//        input = _utf8_encode(input);
//        while (i < input.length()) {
//            chr1 = input.charAt(i++);
//            chr2 = input.charAt(i++);
//            chr3 = input.charAt(i++);
//            enc1 = chr1 >> 2;
//            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
//            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
//            enc4 = chr3 & 63;
//            if (!isNumber(chr2)) {
//                enc3 = enc4 = 64;
//            } else if (!isNumber(chr3)) {
//                enc4 = 64;
//            }
//            output = output +
//                    _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +
//                    _keyStr.charAt(enc3) + _keyStr.charAt(enc4);
//        }
//        return output;
//    }
//
//    private String _utf8_encode(String string){
//        string = string.replaceAll("/\\r\\n/g","\n");
//        String utftext = "";
//        for (int n = 0; n < string.length(); n++) {
//            char c = string.charAt(n);
//            if (c < 128) {
//                utftext += String.valueOf(c);
//            } else if((c > 127) && (c < 2048)) {
//                utftext += String.valueOf((c >> 6) | 192);
//                utftext += String.valueOf((c & 63) | 128);
//            } else {
//                utftext += String.valueOf((c >> 12) | 224);
//                utftext += String.valueOf(((c >> 6) & 63) | 128);
//                utftext += String.valueOf((c & 63) | 128);
//            }
//
//        }
//        return utftext;
//    }
//    public boolean isNumber(char str)
//    {
//        java.util.regex.Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
//        java.util.regex.Matcher match=pattern.matcher(String.valueOf(str));
//        if(match.matches()==false)
//        {
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//
//}
