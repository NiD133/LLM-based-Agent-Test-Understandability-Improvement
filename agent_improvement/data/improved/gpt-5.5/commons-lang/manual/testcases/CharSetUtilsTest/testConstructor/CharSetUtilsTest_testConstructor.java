package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testConstructor extends AbstractLangTest {

    @Test
    void testConstructor() {
        assertNotNull(new CharSetUtils());

        final Constructor<?>[] constructors = CharSetUtils.class.getDeclaredConstructors();
        assertEquals(1, constructors.length);

        final Constructor<?> constructor = constructors[0];
        assertTrue(Modifier.isPublic(constructor.getModifiers()));

        final int classModifiers = CharSetUtils.class.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
        assertFalse(Modifier.isFinal(classModifiers));
    }
}
