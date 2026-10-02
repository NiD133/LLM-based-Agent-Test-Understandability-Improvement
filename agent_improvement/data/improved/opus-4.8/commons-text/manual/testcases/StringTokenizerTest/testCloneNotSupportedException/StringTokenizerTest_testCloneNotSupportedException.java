package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCloneNotSupportedException {

    /**
     * Tests that {@link StringTokenizer#clone()} swallows a {@link CloneNotSupportedException}
     * thrown by {@code cloneReset()} and returns {@code null} instead of propagating it.
     */
    @Test
    void testCloneNotSupportedException() {
        // A tokenizer whose cloneReset() always fails, forcing clone() down its catch branch.
        final StringTokenizer failingTokenizer = new StringTokenizer() {

            @Override
            Object cloneReset() throws CloneNotSupportedException {
                throw new CloneNotSupportedException("test");
            }
        };

        final Object clone = failingTokenizer.clone();

        assertNull(clone, "clone() should return null when cloneReset() throws CloneNotSupportedException");
    }
}
