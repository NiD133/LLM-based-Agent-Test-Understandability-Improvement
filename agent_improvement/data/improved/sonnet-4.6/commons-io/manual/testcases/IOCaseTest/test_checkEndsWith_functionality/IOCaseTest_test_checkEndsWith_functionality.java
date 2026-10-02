package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkEndsWith_functionality {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Nested
    @DisplayName("Valid suffix checks (SENSITIVE)")
    class ValidSuffixChecks {

        @Test
        @DisplayName("Empty suffix always matches any string")
        void emptyStringAlwaysMatchesAsSuffix() {
            assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", ""));
        }

        @Test
        @DisplayName("Exact full-string match is a valid suffix")
        void fullStringMatchIsValidSuffix() {
            assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "ABC"));
        }

        @Test
        @DisplayName("Trailing two-character substring is a valid suffix")
        void twoCharSuffixMatches() {
            assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "BC"));
        }

        @Test
        @DisplayName("Single trailing character is a valid suffix")
        void singleCharSuffixMatches() {
            assertTrue(IOCase.SENSITIVE.checkEndsWith("ABC", "C"));
        }
    }

    @Nested
    @DisplayName("Invalid suffix checks (SENSITIVE)")
    class InvalidSuffixChecks {

        @Test
        @DisplayName("Single leading character is not a suffix")
        void singleLeadingCharIsNotSuffix() {
            assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "A"));
        }

        @Test
        @DisplayName("Leading two-character prefix is not a suffix")
        void twoCharPrefixIsNotSuffix() {
            assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "AB"));
        }

        @Test
        @DisplayName("Suffix longer than string does not match")
        void suffixLongerThanStringDoesNotMatch() {
            assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", "ABCD"));
        }

        @Test
        @DisplayName("Non-empty suffix does not match empty string")
        void nonEmptySuffixDoesNotMatchEmptyString() {
            assertFalse(IOCase.SENSITIVE.checkEndsWith("", "ABC"));
        }
    }

    @Nested
    @DisplayName("Edge cases: empty string as main string")
    class EmptyStringChecks {

        @Test
        @DisplayName("Empty string ends with empty string")
        void emptyStringEndsWithEmptyString() {
            assertTrue(IOCase.SENSITIVE.checkEndsWith("", ""));
        }
    }

    @Nested
    @DisplayName("Null input handling")
    class NullInputHandling {

        @Test
        @DisplayName("Null suffix returns false")
        void nullSuffixReturnsFalse() {
            assertFalse(IOCase.SENSITIVE.checkEndsWith("ABC", null));
        }

        @Test
        @DisplayName("Null string returns false")
        void nullStringReturnsFalse() {
            assertFalse(IOCase.SENSITIVE.checkEndsWith(null, "ABC"));
        }

        @Test
        @DisplayName("Both null returns false")
        void bothNullReturnsFalse() {
            assertFalse(IOCase.SENSITIVE.checkEndsWith(null, null));
        }
    }
}
