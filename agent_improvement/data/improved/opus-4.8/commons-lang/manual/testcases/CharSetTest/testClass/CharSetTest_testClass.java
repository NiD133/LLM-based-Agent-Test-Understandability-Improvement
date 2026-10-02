package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Test;

/**
 * Tests the class-level modifiers of {@link CharSet}.
 *
 * <p>{@code CharSet} is part of the public API and is designed to be
 * extensible (its constructor and the {@code COMMON} map are {@code protected}
 * so that subclasses can add their own patterns). This test guards those two
 * design guarantees:</p>
 * <ul>
 *   <li>the class must be {@code public} so callers can use it, and</li>
 *   <li>the class must not be {@code final} so it can be subclassed.</li>
 * </ul>
 */
public class CharSetTest_testClass extends AbstractLangTest {

    @Test
    void testClass() {
        final int modifiers = CharSet.class.getModifiers();

        assertTrue(Modifier.isPublic(modifiers), "CharSet should be public");
        assertFalse(Modifier.isFinal(modifiers), "CharSet should not be final so it can be subclassed");
    }
}
