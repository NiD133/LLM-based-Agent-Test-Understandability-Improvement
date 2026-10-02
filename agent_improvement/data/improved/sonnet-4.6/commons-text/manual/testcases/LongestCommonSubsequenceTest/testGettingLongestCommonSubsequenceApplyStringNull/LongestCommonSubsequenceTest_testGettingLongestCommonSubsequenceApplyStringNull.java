package org.apache.commons.text.similarity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class LongestCommonSubsequenceTest_testGettingLongestCommonSubsequenceApplyStringNull {

    private static LongestCommonSubsequence lcs;

    @BeforeAll
    public static void setup() {
        lcs = new LongestCommonSubsequence();
    }

    @Test
    @DisplayName("apply() throws IllegalArgumentException when the right (second) argument is null")
    void testApplyThrowsWhenRightArgumentIsNull() {
        // The API contract requires both inputs to be non-null;
        // passing null as the second argument must signal this violation.
        String nonNullLeft = " ";
        String nullRight = null;

        assertThrows(IllegalArgumentException.class, () -> lcs.apply(nonNullLeft, nullRight));
    }
}
