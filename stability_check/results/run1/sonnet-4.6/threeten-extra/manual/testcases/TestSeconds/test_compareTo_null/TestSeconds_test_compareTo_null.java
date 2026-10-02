package org.threeten.extra;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Tests that {@link Seconds#compareTo(Seconds)} throws {@link NullPointerException}
 * when given a null argument, as required by the {@link Comparable} contract.
 */
public class TestSeconds_test_compareTo_null {

    @Test
    public void test_compareTo_null() {
        Seconds fiveSeconds = Seconds.of(5);
        //noinspection DataFlowIssue - intentionally passing null to verify NPE
        assertThrows(NullPointerException.class, () -> fiveSeconds.compareTo(null));
    }
}
