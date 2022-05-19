package common.download;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadFiles {
    public static void downloadFile(String Url, String filePath) {
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(Url).openStream());
             FileOutputStream fileOS = new FileOutputStream(filePath)) {
            byte[] data = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) fileOS.write(data, 0, byteContent);
        } catch (IOException e) {
            // handles IO exceptions
        }
    }
}
