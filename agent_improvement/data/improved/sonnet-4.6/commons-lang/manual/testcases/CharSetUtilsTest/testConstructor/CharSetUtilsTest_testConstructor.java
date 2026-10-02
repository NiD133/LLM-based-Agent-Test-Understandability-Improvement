package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testConstructor extends AbstractLangTest {

    /**
     * Verifies that CharSetUtils follows the expected utility-class structure:
     * - can be instantiated (deprecated public constructor exists for JavaBean tools)
     * - has exactly one declared constructor that is public
     * - the class itself is public and not final
     */
    @Test
    void testConstructor() {
        // Verify the class can be instantiated via its deprecated public constructor
        assertNotNull(new CharSetUtils());

        // Verify exactly one constructor is declared
        final Constructor<?>[] declaredConstructors = CharSetUtils.class.getDeclaredConstructors();
        assertEquals(1, declaredConstructors.length);

        // Verify the sole constructor is public
        final Constructor<?> soleConstructor = declaredConstructors[0];
        assertTrue(Modifier.isPublic(soleConstructor.getModifiers()));

        // Verify the class access modifiers: public and non-final
        final int classModifiers = CharSetUtils.class.getModifiers();
        assertTrue(Modifier.isPublic(classModifiers));
        assertFalse(Modifier.isFinal(classModifiers));
    }
}
