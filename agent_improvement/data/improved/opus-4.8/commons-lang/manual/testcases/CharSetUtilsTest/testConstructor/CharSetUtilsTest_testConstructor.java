package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

/**
 * Verifies the public, no-argument constructor contract of {@link CharSetUtils}.
 *
 * <p>{@code CharSetUtils} is a utility class whose constructor is deliberately kept
 * public (so that JavaBean-based tools can instantiate it) and non-final (so the
 * class can be referenced reflectively). This test pins down those design choices.</p>
 */
public class CharSetUtilsTest_testConstructor extends AbstractLangTest {

    @Test
    void testConstructor() {
        // The public no-arg constructor can be invoked and yields a usable instance.
        assertNotNull(new CharSetUtils());

        // CharSetUtils exposes exactly one constructor, and it is public.
        final Constructor<?>[] constructors = CharSetUtils.class.getDeclaredConstructors();
        assertEquals(1, constructors.length, "CharSetUtils should declare exactly one constructor");
        assertTrue(Modifier.isPublic(constructors[0].getModifiers()),
                "The single constructor should be public");

        // The class itself is public and not final.
        assertTrue(Modifier.isPublic(CharSetUtils.class.getModifiers()),
                "CharSetUtils should be a public class");
        assertFalse(Modifier.isFinal(CharSetUtils.class.getModifiers()),
                "CharSetUtils should not be final");
    }
}
