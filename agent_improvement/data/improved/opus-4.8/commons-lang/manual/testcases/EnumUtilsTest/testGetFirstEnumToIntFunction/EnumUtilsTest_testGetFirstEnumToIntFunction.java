package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.ToIntFunction;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetFirstEnumToIntFunction extends AbstractLangTest {

    /**
     * Maps a {@link Traffic2} constant to its integer value (RED = 1, AMBER = 2, GREEN = 3).
     * {@link EnumUtils#getFirstEnum} uses this function to match a constant by its value.
     */
    private static final ToIntFunction<Traffic2> TO_VALUE = Traffic2::getValue;

    @Test
    void testGetFirstEnumToIntFunction() {
        // A value that matches a constant returns that constant; the default is ignored.
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 1, TO_VALUE, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 2, TO_VALUE, Traffic2.GREEN));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 3, TO_VALUE, Traffic2.RED));

        // A value with no matching constant returns the supplied default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 4, TO_VALUE, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, 5, TO_VALUE, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 6, TO_VALUE, Traffic2.RED));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(Traffic2.class, 0, TO_VALUE, Traffic2.AMBER));
        assertEquals(Traffic2.GREEN, EnumUtils.getFirstEnum(Traffic2.class, -1, TO_VALUE, Traffic2.GREEN));
        assertEquals(Traffic2.RED, EnumUtils.getFirstEnum(Traffic2.class, 0, TO_VALUE, Traffic2.RED));

        // No match and a null default returns null.
        assertNull(EnumUtils.getFirstEnum(Traffic2.class, 7, TO_VALUE, null));

        // A null or non-enum class skips the lookup entirely and returns the default.
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum(null, 1, TO_VALUE, Traffic2.AMBER));
        assertEquals(Traffic2.AMBER, EnumUtils.getFirstEnum((Class) String.class, 1, TO_VALUE, Traffic2.AMBER));
    }
}
