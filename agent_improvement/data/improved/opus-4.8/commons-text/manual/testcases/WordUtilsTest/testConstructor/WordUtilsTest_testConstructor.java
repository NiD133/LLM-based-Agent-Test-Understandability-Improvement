package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

/**
 * Tests the public, default constructor of {@link WordUtils}.
 *
 * <p>{@code WordUtils} is a utility class, so this test documents the
 * intended visibility contract: the class itself is public and non-final,
 * and it exposes exactly one constructor, which is public.</p>
 */
public class WordUtilsTest_testConstructor {

    @Test
    void testConstructor() {
        // The default constructor is callable and yields a usable instance.
        assertNotNull(new WordUtils());

        // WordUtils declares exactly one constructor, and it is public.
        final Constructor<?>[] declaredConstructors = WordUtils.class.getDeclaredConstructors();
        assertEquals(1, declaredConstructors.length, "WordUtils should declare exactly one constructor");
        assertTrue(Modifier.isPublic(declaredConstructors[0].getModifiers()),
                "The single constructor should be public");

        // The class is public so it can be referenced, and non-final so it can be subclassed.
        final int classModifiers = WordUtils.class.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers), "WordUtils should be public");
        assertFalse(Modifier.isFinal(classModifiers), "WordUtils should not be final");
    }
}
