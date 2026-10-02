package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CharSet class-level modifier tests")
public class CharSetTest_testClass extends AbstractLangTest {

    @Test
    @DisplayName("CharSet should be a public class")
    void testCharSetIsPublic() {
        int modifiers = CharSet.class.getModifiers();
        assertTrue(Modifier.isPublic(modifiers));
    }

    @Test
    @DisplayName("CharSet should not be final, allowing subclassing")
    void testCharSetIsNotFinal() {
        int modifiers = CharSet.class.getModifiers();
        assertFalse(Modifier.isFinal(modifiers));
    }
}
