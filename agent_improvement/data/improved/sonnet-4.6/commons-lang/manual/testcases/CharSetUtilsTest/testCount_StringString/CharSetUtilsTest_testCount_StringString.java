package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testCount_StringString extends AbstractLangTest {

    @Nested
    class WhenStringOrSetIsNullOrEmpty {

        @Test
        void nullStringAndNullSetReturnsZero() {
            assertEquals(0, CharSetUtils.count(null, (String) null));
        }

        @Test
        void nullStringAndEmptySetReturnsZero() {
            assertEquals(0, CharSetUtils.count(null, ""));
        }

        @Test
        void emptyStringAndNullSetReturnsZero() {
            assertEquals(0, CharSetUtils.count("", (String) null));
        }

        @Test
        void emptyStringAndEmptySetReturnsZero() {
            assertEquals(0, CharSetUtils.count("", ""));
        }

        @Test
        void emptyStringAndValidSetReturnsZero() {
            assertEquals(0, CharSetUtils.count("", "a-e"));
        }

        @Test
        void nonEmptyStringAndNullSetReturnsZero() {
            assertEquals(0, CharSetUtils.count("hello", (String) null));
        }

        @Test
        void nonEmptyStringAndEmptySetReturnsZero() {
            assertEquals(0, CharSetUtils.count("hello", ""));
        }
    }

    @Nested
    class WhenStringAndSetAreValid {

        @Test
        void countsOneMatchingCharInRange() {
            // "hello" contains only 'e' in the range "a-e"
            assertEquals(1, CharSetUtils.count("hello", "a-e"));
        }

        @Test
        void countsMultipleMatchingCharsInRange() {
            // "hello" contains 'l', 'l', and 'o' in the range "l-p"
            assertEquals(3, CharSetUtils.count("hello", "l-p"));
        }
    }
}
