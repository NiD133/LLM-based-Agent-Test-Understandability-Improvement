package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

public class CharRangeTest_testClass extends AbstractLangTest {

    @Test
    void testClass() {
        // CharRange is an internal implementation type and must stay package-private.
        assertFalse(Modifier.isPublic(CharRange.class.getModifiers()));

        // CharRange instances are immutable, so the class itself must remain final.
        assertTrue(Modifier.isFinal(CharRange.class.getModifiers()));
    }
}
