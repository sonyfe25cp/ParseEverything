package webpageparser.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class Utils {

    public static final String getResouce(String name) {
        try (InputStream is = Utils.class.getClassLoader().getResourceAsStream(name)) {
            return IOUtils.toString(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
