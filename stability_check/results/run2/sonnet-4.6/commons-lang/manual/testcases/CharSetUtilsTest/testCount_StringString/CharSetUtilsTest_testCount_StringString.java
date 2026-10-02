package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testCount_StringString extends AbstractLangTest {

    @Nested
    class WhenStringIsNull {

        @Test
        void returnsZeroForNullSet() {
            assertEquals(0, CharSetUtils.count(null, (String) null));
        }

        @Test
        void returnsZeroForEmptySet() {
            assertEquals(0, CharSetUtils.count(null, ""));
        }
    }

    @Nested
    class WhenStringIsEmpty {

        @Test
        void returnsZeroForNullSet() {
            assertEquals(0, CharSetUtils.count("", (String) null));
        }

        @Test
        void returnsZeroForEmptySet() {
            assertEquals(0, CharSetUtils.count("", ""));
        }

        @Test
        void returnsZeroForCharacterRangeSet() {
            assertEquals(0, CharSetUtils.count("", "a-e"));
        }
    }

    @Nested
    class WhenSetIsNullOrEmpty {

        @Test
        void returnsZeroForNullSet() {
            assertEquals(0, CharSetUtils.count("hello", (String) null));
        }

        @Test
        void returnsZeroForEmptySet() {
            assertEquals(0, CharSetUtils.count("hello", ""));
        }
    }

    @Nested
    class WhenCountingMatchingCharacters {

        @Test
        void countsOneMatchInRangeAtoE() {
            // "hello" contains only 'e' from the range a-e
            assertEquals(1, CharSetUtils.count("hello", "a-e"));
        }

        @Test
        void countsThreeMatchesInRangeLtoP() {
            // "hello" contains 'l', 'l', 'o' from the range l-p
            assertEquals(3, CharSetUtils.count("hello", "l-p"));
        }
    }
}
