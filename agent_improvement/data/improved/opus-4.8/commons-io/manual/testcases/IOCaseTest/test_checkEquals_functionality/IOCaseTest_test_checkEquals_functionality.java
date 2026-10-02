package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Verifies {@link IOCase#checkEquals(String, String)} for the case-sensitive
 * comparison rule ({@link IOCase#SENSITIVE}).
 * <p>
 * Under the case-sensitive rule, two strings are equal only when they match
 * exactly (same length and same characters). The method also defines a
 * null-safe contract: two nulls are equal, while a null compared with a
 * non-null string is not.
 * </p>
 */
public class IOCaseTest_test_checkEquals_functionality {

    /** The string compared against in the partial-match cases. */
    private static final String REFERENCE = "ABC";

    @Test
    void test_checkEquals_functionality() {
        // Only an exact match is considered equal under the case-sensitive rule.
        assertTrue(IOCase.SENSITIVE.checkEquals(REFERENCE, "ABC"));

        // Prefixes, suffixes, substrings and supersets are all unequal.
        assertFalse(IOCase.SENSITIVE.checkEquals(REFERENCE, ""));
        assertFalse(IOCase.SENSITIVE.checkEquals(REFERENCE, "A"));
        assertFalse(IOCase.SENSITIVE.checkEquals(REFERENCE, "AB"));
        assertFalse(IOCase.SENSITIVE.checkEquals(REFERENCE, "BC"));
        assertFalse(IOCase.SENSITIVE.checkEquals(REFERENCE, "C"));
        assertFalse(IOCase.SENSITIVE.checkEquals(REFERENCE, "ABCD"));

        // The empty string equals only itself.
        assertFalse(IOCase.SENSITIVE.checkEquals("", REFERENCE));
        assertTrue(IOCase.SENSITIVE.checkEquals("", ""));

        // Null-safe contract: a null and a non-null are unequal; two nulls are equal.
        assertFalse(IOCase.SENSITIVE.checkEquals(REFERENCE, null));
        assertFalse(IOCase.SENSITIVE.checkEquals(null, REFERENCE));
        assertTrue(IOCase.SENSITIVE.checkEquals(null, null));
    }
}
