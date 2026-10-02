package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkRegionMatches_functionality {

    @Nested
    class WhenOffsetIsZero {

        @Test
        void matchesEmptySearch() {
            assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, ""));
        }

        @Test
        void matchesSingleCharPrefix() {
            assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "A"));
        }

        @Test
        void matchesTwoCharPrefix() {
            assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "AB"));
        }

        @Test
        void matchesFullString() {
            assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABC"));
        }

        @Test
        void doesNotMatchSearchStartingAtLaterPosition() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "BC"));
        }

        @Test
        void doesNotMatchSearchStartingAtEnd() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "C"));
        }

        @Test
        void doesNotMatchSearchLongerThanString() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, "ABCD"));
        }

        @Test
        void doesNotMatchNonEmptySearchAgainstEmptyString() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 0, "ABC"));
        }

        @Test
        void matchesEmptySearchAgainstEmptyString() {
            assertTrue(IOCase.SENSITIVE.checkRegionMatches("", 0, ""));
        }
    }

    @Nested
    class WhenOffsetIsOne {

        @Test
        void matchesEmptySearch() {
            assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, ""));
        }

        @Test
        void matchesSubstringStartingAtOffset() {
            assertTrue(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "BC"));
        }

        @Test
        void doesNotMatchPrefixThatDoesNotStartAtOffset() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "A"));
        }

        @Test
        void doesNotMatchTwoCharPrefixThatDoesNotStartAtOffset() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "AB"));
        }

        @Test
        void doesNotMatchFullStringWhenOffsetExcludesFirstChar() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABC"));
        }

        @Test
        void doesNotMatchSingleCharNotAtOffset() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "C"));
        }

        @Test
        void doesNotMatchSearchLongerThanString() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, "ABCD"));
        }

        @Test
        void doesNotMatchNonEmptySearchAgainstEmptyString() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, "ABC"));
        }

        @Test
        void doesNotMatchEmptySearchWhenOffsetIsOutOfBounds() {
            // offset 1 exceeds the length of "", so no region can match
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("", 1, ""));
        }
    }

    @Nested
    class WhenInputsAreNull {

        @Test
        void returnsFalseWhenSearchIsNull() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 0, null));
        }

        @Test
        void returnsFalseWhenStringIsNull() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, "ABC"));
        }

        @Test
        void returnsFalseWhenBothInputsAreNull() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 0, null));
        }

        @Test
        void returnsFalseWhenSearchIsNullAtOffset1() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches("ABC", 1, null));
        }

        @Test
        void returnsFalseWhenStringIsNullAtOffset1() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, "ABC"));
        }

        @Test
        void returnsFalseWhenBothInputsAreNullAtOffset1() {
            assertFalse(IOCase.SENSITIVE.checkRegionMatches(null, 1, null));
        }
    }
}
