package net.oldschoolminecraft.nlib.utils;

import java.util.*;
import java.io.*;

public class StringUtils
{
    public static String implode(final String[] array, final String separator) {
        if (array.length == 0) {
            return "";
        }
        final StringBuilder buffer = new StringBuilder();
        for (final String str : array) {
            buffer.append(separator);
            buffer.append(str);
        }
        return buffer.substring(separator.length()).trim();
    }

    public static String implode(final List<?> list, final String separator) {
        if (list.isEmpty()) {
            return "";
        }
        final StringBuilder builder = new StringBuilder();
        final int lastElement = list.size() - 1;
        for (int i = 0; i < list.size(); ++i) {
            builder.append(list.get(0).toString());
            if (i < lastElement) {
                builder.append(separator);
            }
        }
        for (final Object obj : list) {
            builder.append(obj.toString()).append(separator);
        }
        return builder.toString();
    }

    public static String readStream(final InputStream is) throws Exception {
        if (is != null) {
            final StringWriter writer = new StringWriter();
            try {
                final Reader reader = new InputStreamReader(is, "UTF-8");
                final char[] buffer = new char[128];
                int read = 0;
                while ((read = reader.read(buffer)) > 0) {
                    writer.write(buffer, 0, read);
                }
            }
            finally {
                is.close();
            }
            return writer.toString();
        }
        return null;
    }

    public static String repeat(final String str, final int times) {
        final StringBuilder buffer = new StringBuilder(times * str.length());
        for (int i = 0; i < times; ++i) {
            buffer.append(str);
        }
        return buffer.toString();
    }

    public static int toInteger(final String value, final int defaultValue) {
        if (value == null || value.isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        }
        catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
