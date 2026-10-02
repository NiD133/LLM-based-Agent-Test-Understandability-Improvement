package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class StringTokenizerTest_testCloneNotSupportedException {

    private static final class CloneResetFailsTokenizer extends StringTokenizer {

        @Override
        Object cloneReset() throws CloneNotSupportedException {
            throw new CloneNotSupportedException("test");
        }
    }

    /**
     * Tests that {@link StringTokenizer#clone()} catches {@link CloneNotSupportedException}
     * from {@link StringTokenizer#cloneReset()} and returns {@code null}.
     */
    @Test
    void testCloneNotSupportedException() {
        final Object clonedTokenizer = new CloneResetFailsTokenizer().clone();

        assertNull(clonedTokenizer);
    }
}
