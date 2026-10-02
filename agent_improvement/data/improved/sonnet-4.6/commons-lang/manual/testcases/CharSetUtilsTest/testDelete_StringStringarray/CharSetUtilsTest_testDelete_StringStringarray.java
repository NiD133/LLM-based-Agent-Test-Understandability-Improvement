package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CharSetUtilsTest_testDelete_StringStringarray extends AbstractLangTest {

    @Nested
    class WhenInputStringIsNull {
        @Test
        void returnsNullForNullSet() {
            assertNull(CharSetUtils.delete(null, (String[]) null));
        }

        @Test
        void returnsNullForNoSet() {
            assertNull(CharSetUtils.delete(null));
        }

        @Test
        void returnsNullForNullSetElement() {
            assertNull(CharSetUtils.delete(null, (String) null));
        }

        @Test
        void returnsNullForNonEmptySet() {
            assertNull(CharSetUtils.delete(null, "el"));
        }
    }

    @Nested
    class WhenInputStringIsEmpty {
        @Test
        void returnsEmptyForNullSet() {
            assertEquals("", CharSetUtils.delete("", (String[]) null));
        }

        @Test
        void returnsEmptyForNoSet() {
            assertEquals("", CharSetUtils.delete(""));
        }

        @Test
        void returnsEmptyForNullSetElement() {
            assertEquals("", CharSetUtils.delete("", (String) null));
        }

        @Test
        void returnsEmptyForNonEmptySet() {
            assertEquals("", CharSetUtils.delete("", "a-e"));
        }
    }

    @Nested
    class WhenSetIsNullOrEmpty {
        @Test
        void returnsOriginalStringForNullArray() {
            assertEquals("hello", CharSetUtils.delete("hello", (String[]) null));
        }

        @Test
        void returnsOriginalStringForNoArgs() {
            assertEquals("hello", CharSetUtils.delete("hello"));
        }

        @Test
        void returnsOriginalStringForNullElement() {
            assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        }

        @Test
        void returnsOriginalStringForEmptySetString() {
            assertEquals("hello", CharSetUtils.delete("hello", ""));
        }
    }

    @Nested
    class WhenDeletingCharacters {
        @Test
        void deletesNoCharsWhenSetHasNoMatch() {
            assertEquals("hello", CharSetUtils.delete("hello", "xyz"));
        }

        @Test
        void deletesSpecifiedCharacters() {
            assertEquals("ho", CharSetUtils.delete("hello", "el"));
        }

        @Test
        void deletesAllCharsWhenSetMatchesAll() {
            assertEquals("", CharSetUtils.delete("hello", "elho"));
        }

        @Test
        void deletesAllCharsUsingRange() {
            assertEquals("", CharSetUtils.delete("hello", "a-z"));
        }

        @Test
        void deletesDashCharacter() {
            assertEquals("", CharSetUtils.delete("----", "-"));
        }

        @Test
        void deletesRepeatedCharacter() {
            assertEquals("heo", CharSetUtils.delete("hello", "l"));
        }
    }
}
