package com.hydra.core.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class IOUtil {
    public static void closeSilence(Closeable obj) {
        if (null != obj) {
            try {
                obj.close();
            } catch (Throwable t) {
                log.error("Close exception: " + t.getMessage());
            }
        }
    }

    public static Object deserialize(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return ois.readObject();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to deserialize object", ex);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException("Failed to deserialize object type", ex);
        } finally {
            closeSilence(ois);
        }
    }

    public static byte[] serialize(final Object obj) {
        if (null == obj) {
            return null;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to serialize object of type: " + obj.getClass(), ex);
        } finally {
            closeSilence(baos);
        }
    }

    private IOUtil() {
        throw new UnsupportedOperationException();
    }
}
