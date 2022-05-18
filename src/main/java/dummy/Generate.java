package dummy;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Generate {

    public static String generateHalfWidthString(int length) {
        return RandomStringUtils.random(length, true, true);
    }

    public static String generateFullWidthString(int length) {
        return RandomStringUtils.random(length, 0x4e00, 0x4f80, true, false);
    }

    /**
     *
     * @return in range (0,max-1)
     */
    public static Integer generateOptionID(int max) {
        return RandomUtils.nextInt(max);
    }

    /**
     * @param dateFormat yyyy_MM_dd-hh_mm_ss
     */
    public static String generateDateTime(String dateFormat) {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
        return dateTimeFormatter.format(dateTime);
    }

    public static String generateNumberString(int length) {
        return RandomStringUtils.random(length, false, true);
    }
}
