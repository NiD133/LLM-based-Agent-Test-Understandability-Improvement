package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

public class WordUtilsTest_testConstructor {

    @Test
    void testConstructor() {
        // WordUtils should be instantiable (it is a utility class with a public constructor)
        assertNotNull(new WordUtils());

        final Constructor<?>[] cons = WordUtils.class.getDeclaredConstructors();

        // WordUtils should declare exactly one constructor
        assertEquals(1, cons.length);

        // The single constructor must be public so callers can instantiate it
        assertTrue(Modifier.isPublic(cons[0].getModifiers()));

        // The class itself must be public so it is accessible from other packages
        assertTrue(Modifier.isPublic(WordUtils.class.getModifiers()));

        // The class must not be final so it can be subclassed if needed
        assertFalse(Modifier.isFinal(WordUtils.class.getModifiers()));
    }
}
