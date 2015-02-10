package lt.jsj.qust.jjlin.util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Adam on 2015/1/3.
 */
public class Utils {
    /**
     * String to HMAC - MD5
     *
     * @param s
     * @param keyString
     * @return
     */
    public static String sStringToHMACMD5(String s, String keyString) {
        String sEncodedString = null;
        try {
            SecretKeySpec key = new SecretKeySpec(
                    (keyString).getBytes("UTF-8"), "HmacMD5");
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(key);

            byte[] bytes = mac.doFinal(s.getBytes("ASCII"));

            StringBuffer hash = new StringBuffer();

            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(0xFF & bytes[i]);
                if (hex.length() == 1) {
                    hash.append('0');
                }
                hash.append(hex);
            }
            sEncodedString = hash.toString();
        } catch (UnsupportedEncodingException e) {
        } catch (InvalidKeyException e) {
        } catch (NoSuchAlgorithmException e) {
        }
        return sEncodedString;
    }

//    public static String getArtistDisplay(Artist[] mArtists) {
//        StringBuilder mStrBuilder = new StringBuilder();
//        if (mArtists != null) {
//            int numberArtist = mArtists.length;
//            if (numberArtist > 2) {
//                for (int i = 0; i < numberArtist; i++) {
//                    if (mArtists[i] != null) {
//                        if (i == numberArtist - 1) {
//                            if (!mArtists[i].getArtistName().isEmpty()) {
//                                mStrBuilder.append(mArtists[i].getArtistName());
//                            }
//                        } else {
//                            if (!mArtists[i].getArtistName().isEmpty()) {
//                                mStrBuilder.append(mArtists[i].getArtistName() + ", ");
//                            }
//                        }
//                    }
//                }
//            } else if (numberArtist == 2) {
//                if (mArtists[0] != null && !mArtists[0].getArtistName().isEmpty()) {
//                    mStrBuilder.append(mArtists[0].getArtistName());
//
//                }
//                if (mArtists[1] != null && !mArtists[1].getArtistName().isEmpty()) {
//                    mStrBuilder.append(" ft. ");
//                    mStrBuilder.append(mArtists[numberArtist - 1].getArtistName());
//                }
//            } else if (numberArtist == 1) {
//                if (mArtists[0] != null) {
//                    mStrBuilder.append(mArtists[0].getArtistName());
//                } else {
//                    mStrBuilder.append("Unknown");
//                }
//            } else {
//                mStrBuilder.append("Unknown");
//            }
//        } else {
//            mStrBuilder.append("Unknown");
//        }
//        return mStrBuilder.toString();
//    }

    /*
     * Try to use String.format() as little as possible, because it creates a
     * new Formatter every time you call it, which is very inefficient. Reusing
     * an existing Formatter more than tripled the speed of makeTimeString().
     * This Formatter/StringBuilder are also used by makeAlbumSongsLabel()
     */
    private static StringBuilder sStrBuilder;

    public static String makeTimeString(long milliseconds) {
        sStrBuilder = new StringBuilder();
        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            sStrBuilder.append(hours).append(":");
        }
        if (minutes < 10) {
            sStrBuilder.append("0").append(minutes).append(":");
        } else {
            sStrBuilder.append(minutes);
        }
        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            sStrBuilder.append("0").append(seconds);
        } else {
            sStrBuilder.append(seconds);
        }
        // return timer string
        return sStrBuilder.toString();
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public static int getProgressPercentage(int currentDuration,
                                            int totalDuration) {
        Double percentage = (double) 0;

        int currentSeconds = currentDuration / 1000;
        int totalSeconds = totalDuration / 1000;

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public static int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }
}
