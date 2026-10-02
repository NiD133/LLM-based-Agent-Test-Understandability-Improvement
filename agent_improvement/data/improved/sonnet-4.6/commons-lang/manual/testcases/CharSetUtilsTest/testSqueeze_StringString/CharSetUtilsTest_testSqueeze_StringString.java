package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testSqueeze_StringString extends AbstractLangTest {

    @Nested
    class WhenInputStringIsNull {

        @Test
        void returnsNullForNullSet() {
            assertNull(CharSetUtils.squeeze(null, (String) null));
        }

        @Test
        void returnsNullForEmptySet() {
            assertNull(CharSetUtils.squeeze(null, ""));
        }
    }

    @Nested
    class WhenInputStringIsEmpty {

        @Test
        void returnsEmptyForNullSet() {
            assertEquals("", CharSetUtils.squeeze("", (String) null));
        }

        @Test
        void returnsEmptyForEmptySet() {
            assertEquals("", CharSetUtils.squeeze("", ""));
        }

        @Test
        void returnsEmptyForRangeSet() {
            assertEquals("", CharSetUtils.squeeze("", "a-e"));
        }
    }

    @Nested
    class WhenNoSqueezeOccurs {

        @Test
        void returnsUnchangedForNullSet() {
            assertEquals("hello", CharSetUtils.squeeze("hello", (String) null));
        }

        @Test
        void returnsUnchangedForEmptySet() {
            assertEquals("hello", CharSetUtils.squeeze("hello", ""));
        }

        @Test
        void returnsUnchangedWhenNoConsecutiveDuplicatesInSet() {
            // 'h','e','l','l','o' — the repeated 'l' is not in range "a-e"
            assertEquals("hello", CharSetUtils.squeeze("hello", "a-e"));
        }
    }

    @Nested
    class WhenSqueezeOccurs {

        @Test
        void squeezesConsecutiveCharsMatchedByRange() {
            // 'l' falls in "l-p", so the double 'l' in "hello" is collapsed to one
            assertEquals("helo", CharSetUtils.squeeze("hello", "l-p"));
        }

        @Test
        void squeezesOnlyTheSpecifiedLiteralChar() {
            // only 'l' is in set, so "ll" collapses but "oo" stays
            assertEquals("heloo", CharSetUtils.squeeze("helloo", "l"));
        }

        @Test
        void squeezesCharsNotMatchedByNegatedSet() {
            // "^l" means everything except 'l'; "oo" is not 'l', so it collapses
            assertEquals("hello", CharSetUtils.squeeze("helloo", "^l"));
        }
    }
}
