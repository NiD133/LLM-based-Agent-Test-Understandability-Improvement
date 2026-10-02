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
        assertNotNull(new WordUtils());

        final Constructor<?>[] constructors = WordUtils.class.getDeclaredConstructors();
        assertEquals(1, constructors.length);

        final int constructorModifiers = constructors[0].getModifiers();
        assertTrue(Modifier.isPublic(constructorModifiers));

        final int classModifiers = WordUtils.class.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
        assertFalse(Modifier.isFinal(classModifiers));
    }
}
