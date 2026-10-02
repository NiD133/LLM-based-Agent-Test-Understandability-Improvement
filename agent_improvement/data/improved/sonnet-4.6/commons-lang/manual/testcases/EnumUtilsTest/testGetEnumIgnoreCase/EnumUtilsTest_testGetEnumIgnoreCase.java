package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testGetEnumIgnoreCase extends AbstractLangTest {

    @Test
    void testGetEnumIgnoreCase_allLowercase_matchesEnumConstant() {
        assertEquals(Traffic.RED, EnumUtils.getEnumIgnoreCase(Traffic.class, "red"));
    }

    @Test
    void testGetEnumIgnoreCase_mixedCaseFirstLetterUpper_matchesEnumConstant() {
        assertEquals(Traffic.AMBER, EnumUtils.getEnumIgnoreCase(Traffic.class, "Amber"));
    }

    @Test
    void testGetEnumIgnoreCase_mixedCaseArbitrary_matchesEnumConstant() {
        assertEquals(Traffic.GREEN, EnumUtils.getEnumIgnoreCase(Traffic.class, "grEEn"));
    }

    @Test
    void testGetEnumIgnoreCase_nonExistentName_returnsNull() {
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, "purple"));
    }

    @Test
    void testGetEnumIgnoreCase_nullName_returnsNull() {
        assertNull(EnumUtils.getEnumIgnoreCase(Traffic.class, null));
    }
}
