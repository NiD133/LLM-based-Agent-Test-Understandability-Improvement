package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CharRangeTest_testContainsNullArg extends AbstractLangTest {

    @Test
    @DisplayName("contains(null) throws NullPointerException with message identifying the parameter name")
    void testContainsNullArg() {
        // CharRange.contains(CharRange) calls Objects.requireNonNull(range, "range"),
        // so the exception message should be the parameter name "range".
        final CharRange range = CharRange.is('a');
        final NullPointerException e = assertNullPointerException(() -> range.contains(null));
        assertEquals("range", e.getMessage());
    }
}
