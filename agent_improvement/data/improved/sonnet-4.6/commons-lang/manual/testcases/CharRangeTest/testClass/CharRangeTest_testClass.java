package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testClass extends AbstractLangTest {

    /**
     * CharRange was made non-public in version 3.0 to restrict it to package-internal use.
     * This test ensures that access-level contract has not been accidentally widened.
     */
    @Test
    void testCharRangeClassIsNotPublic() {
        int modifiers = CharRange.class.getModifiers();
        assertFalse(Modifier.isPublic(modifiers));
    }

    /**
     * CharRange is declared final so that its immutability guarantee cannot be broken
     * by a subclass. This test ensures the final modifier is still present.
     */
    @Test
    void testCharRangeClassIsFinal() {
        int modifiers = CharRange.class.getModifiers();
        assertTrue(Modifier.isFinal(modifiers));
    }
}
