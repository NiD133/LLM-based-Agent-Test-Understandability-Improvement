package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnum extends AbstractLangTest {

    @Test
    @DisplayName("getEnum returns the matching constant for valid names and null for unknown or null names")
    void testGetEnum() {
        // Valid names return the corresponding enum constant
        assertEquals(Traffic.RED,   EnumUtils.getEnum(Traffic.class, "RED"));
        assertEquals(Traffic.AMBER, EnumUtils.getEnum(Traffic.class, "AMBER"));
        assertEquals(Traffic.GREEN, EnumUtils.getEnum(Traffic.class, "GREEN"));

        // Non-existent name returns null instead of throwing
        assertNull(EnumUtils.getEnum(Traffic.class, "PURPLE"));

        // Null name returns null instead of throwing
        assertNull(EnumUtils.getEnum(Traffic.class, null));
    }
}
