package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

public class CharSetTest_testClass extends AbstractLangTest {

    @Test
    void testClass() {
        // CharSet is designed for subclassing but remains part of the public API.
        assertTrue(Modifier.isPublic(CharSet.class.getModifiers()));
        assertFalse(Modifier.isFinal(CharSet.class.getModifiers()));
    }
}
