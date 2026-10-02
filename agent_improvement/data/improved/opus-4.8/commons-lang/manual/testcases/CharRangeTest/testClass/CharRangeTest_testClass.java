package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

/**
 * Tests the class-level modifiers of {@link CharRange}.
 *
 * <p>Since version 3.0, {@code CharRange} is intended to be an internal,
 * immutable helper type. This is enforced by declaring the class as
 * package-private (non-public) and {@code final}.</p>
 */
public class CharRangeTest_testClass extends AbstractLangTest {

    /**
     * Verifies that {@link CharRange} is neither publicly accessible nor
     * subclassable: it must be non-public and final.
     */
    @Test
    void testClass() {
        final int modifiers = CharRange.class.getModifiers();

        // Made non-public in 3.0 so the type stays an internal helper.
        assertFalse(Modifier.isPublic(modifiers));
        // Declared final so it cannot be subclassed.
        assertTrue(Modifier.isFinal(modifiers));
    }
}
