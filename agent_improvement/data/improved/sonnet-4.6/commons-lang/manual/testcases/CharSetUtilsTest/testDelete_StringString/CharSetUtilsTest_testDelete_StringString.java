package org.apache.commons.lang3;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("CharSetUtils.delete(String, String)")
public class CharSetUtilsTest_testDelete_StringString extends AbstractLangTest {

    @Nested
    @DisplayName("when the input string is null")
    class NullInputString {

        @Test
        @DisplayName("returns null regardless of the set argument")
        void returnsNullForNullSet() {
            assertNull(CharSetUtils.delete(null, (String) null));
        }

        @Test
        @DisplayName("returns null when set is an empty string")
        void returnsNullForEmptySet() {
            assertNull(CharSetUtils.delete(null, ""));
        }
    }

    @Nested
    @DisplayName("when the input string is empty")
    class EmptyInputString {

        @Test
        @DisplayName("returns empty string when set is null")
        void returnsEmptyForNullSet() {
            assertEquals("", CharSetUtils.delete("", (String) null));
        }

        @Test
        @DisplayName("returns empty string when set is empty")
        void returnsEmptyForEmptySet() {
            assertEquals("", CharSetUtils.delete("", ""));
        }

        @Test
        @DisplayName("returns empty string when set specifies a character range")
        void returnsEmptyForCharRange() {
            assertEquals("", CharSetUtils.delete("", "a-e"));
        }
    }

    @Nested
    @DisplayName("when the set is null or empty")
    class NullOrEmptySet {

        @Test
        @DisplayName("returns the original string unchanged when set is null")
        void returnsOriginalForNullSet() {
            assertEquals("hello", CharSetUtils.delete("hello", (String) null));
        }

        @Test
        @DisplayName("returns the original string unchanged when set is empty")
        void returnsOriginalForEmptySet() {
            assertEquals("hello", CharSetUtils.delete("hello", ""));
        }
    }

    @Nested
    @DisplayName("when the set specifies a character range")
    class CharacterRangeDeletion {

        @Test
        @DisplayName("deletes characters that fall within the set range 'a-e'")
        void deletesCharactersInRange() {
            // 'e' in "hello" is in range a-e and is removed; 'h','l','l','o' are kept
            assertEquals("hllo", CharSetUtils.delete("hello", "a-e"));
        }

        @Test
        @DisplayName("deletes characters that fall within the set range 'l-p'")
        void deletesMultipleCharactersInRange() {
            // 'l','l','o' in "hello" are in range l-p and are removed; 'h','e' are kept
            assertEquals("he", CharSetUtils.delete("hello", "l-p"));
        }

        @Test
        @DisplayName("returns the original string when no character matches the set")
        void returnsOriginalWhenNoMatch() {
            assertEquals("hello", CharSetUtils.delete("hello", "z"));
        }
    }
}
