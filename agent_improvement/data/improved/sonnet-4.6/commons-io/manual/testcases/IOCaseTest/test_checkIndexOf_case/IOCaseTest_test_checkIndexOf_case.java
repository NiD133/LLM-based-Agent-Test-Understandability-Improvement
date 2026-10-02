package org.apache.commons.io;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.File;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class IOCaseTest_test_checkIndexOf_case {

    private static final boolean WINDOWS = File.separatorChar == '\\';

    @Nested
    class WhenSensitive {

        @Test
        void findsSubstringAtExpectedIndex() {
            assertEquals(1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "BC"));
        }

        @Test
        void doesNotFindDifferentCaseSubstring() {
            assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, "Bc"));
        }

        @Test
        void returnsMinusOneForNullStr() {
            assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, "Bc"));
        }

        @Test
        void returnsMinusOneForBothNulls() {
            assertEquals(-1, IOCase.SENSITIVE.checkIndexOf(null, 0, null));
        }

        @Test
        void returnsMinusOneForNullSearch() {
            assertEquals(-1, IOCase.SENSITIVE.checkIndexOf("ABC", 0, null));
        }
    }

    @Nested
    class WhenInsensitive {

        @Test
        void findsExactCaseSubstring() {
            assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "BC"));
        }

        @Test
        void findsDifferentCaseSubstring() {
            assertEquals(1, IOCase.INSENSITIVE.checkIndexOf("ABC", 0, "Bc"));
        }
    }

    @Nested
    class WhenSystem {

        @Test
        void findsExactCaseSubstring() {
            assertEquals(1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "BC"));
        }

        @Test
        void findsOrMissesDifferentCaseSubstringBasedOnPlatform() {
            assertEquals(WINDOWS ? 1 : -1, IOCase.SYSTEM.checkIndexOf("ABC", 0, "Bc"));
        }
    }
}
