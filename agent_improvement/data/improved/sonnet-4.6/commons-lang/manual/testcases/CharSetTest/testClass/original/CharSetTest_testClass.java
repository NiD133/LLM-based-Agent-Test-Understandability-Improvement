package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Modifier;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class CharSetTest_testClass extends AbstractLangTest {

    @Test
    void testClass() {
        assertTrue(Modifier.isPublic(CharSet.class.getModifiers()));
        assertFalse(Modifier.isFinal(CharSet.class.getModifiers()));
    }
}
