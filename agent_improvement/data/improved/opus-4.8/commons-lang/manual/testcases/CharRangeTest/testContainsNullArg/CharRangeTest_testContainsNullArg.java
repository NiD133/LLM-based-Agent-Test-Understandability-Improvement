package org.apache.commons.lang3;

import static org.apache.commons.lang3.LangAssertions.assertNullPointerException;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link CharRange#contains(CharRange)} rejects a {@code null} range.
 */
public class CharRangeTest_testContainsNullArg extends AbstractLangTest {

    @Test
    void contains_throwsNullPointerException_whenRangeArgumentIsNull() {
        final CharRange range = CharRange.is('a');

        // Passing a null CharRange must be rejected with a NullPointerException.
        final NullPointerException thrown =
                assertNullPointerException(() -> range.contains((CharRange) null));

        // The exception names the offending parameter ("range").
        assertEquals("range", thrown.getMessage());
    }
}
