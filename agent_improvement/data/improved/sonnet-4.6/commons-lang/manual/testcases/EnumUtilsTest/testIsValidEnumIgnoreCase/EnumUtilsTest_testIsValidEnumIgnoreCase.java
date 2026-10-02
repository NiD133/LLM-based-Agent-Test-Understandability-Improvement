package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class EnumUtilsTest_testIsValidEnumIgnoreCase extends AbstractLangTest {

    @Test
    void testIsValidEnumIgnoreCase_allLowercase_matchesEnumConstant() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "red"));
    }

    @Test
    void testIsValidEnumIgnoreCase_leadingUppercase_matchesEnumConstant() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "Amber"));
    }

    @Test
    void testIsValidEnumIgnoreCase_mixedCase_matchesEnumConstant() {
        assertTrue(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "grEEn"));
    }

    @Test
    void testIsValidEnumIgnoreCase_nonExistentName_returnsFalse() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, "purple"));
    }

    @Test
    void testIsValidEnumIgnoreCase_nullName_returnsFalse() {
        assertFalse(EnumUtils.isValidEnumIgnoreCase(Traffic.class, null));
    }
}
