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

    public static Integer generateOptionID(int max) {
        return RandomUtils.nextInt(max);
    }

    public static String generateDateTimeYM() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        return dateTimeFormatter.format(dateTime);
    }

    public static String generateNumberString(int length) {
        return RandomStringUtils.random(length, false, true);
    }
}
