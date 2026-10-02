package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase_defaultEnum extends AbstractLangTest {

    @Test
    void getEnumIgnoreCase_matchFound_returnsMatchedEnum() {
        assertEquals(Traffic.RED,   EnumUtils.getEnumIgnoreCase(Traffic.class, "red",   Traffic.AMBER));
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber", Traffic.GREEN));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn", Traffic.RED));
    }

    @Test
    void getEnumIgnoreCase_noMatch_returnsDefaultEnum() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "purple", Traffic.GREEN));
        assertEquals(Traffic.RED,   EnumUtils.getEnumIgnoreCase(Traffic.class, "pUrPlE", Traffic.RED));
    }

    @Test
    void getEnumIgnoreCase_nullName_returnsDefaultEnum() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.AMBER));
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.GREEN));
        assertEquals(Traffic.RED,   EnumUtils.getEnumIgnoreCase(Traffic.class, null, Traffic.RED));
    }

    @Test
    void getEnumIgnoreCase_nullDefaultEnum_returnsNullWhenNoMatch() {
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "PURPLE", null));
    }

    @Test
    void getEnumIgnoreCase_nullEnumClass_returnsNull() {
        assertNull(EnumUtils.getEnumIgnoreCase(null, "PURPLE", null));
    }
}
