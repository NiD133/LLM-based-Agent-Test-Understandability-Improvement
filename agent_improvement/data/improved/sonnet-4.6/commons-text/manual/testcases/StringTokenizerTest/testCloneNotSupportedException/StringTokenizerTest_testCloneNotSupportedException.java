package org.apache.commons.text;

import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

/**
 * Tests the error-handling path in {@link StringTokenizer#clone()}: when the internal
 * {@code cloneReset()} call raises {@link CloneNotSupportedException}, the public
 * {@code clone()} method must swallow the exception and return {@code null} instead of
 * propagating it.
 */
public class StringTokenizerTest_testCloneNotSupportedException {

    /**
     * Verifies that {@link StringTokenizer#clone()} returns {@code null} when the
     * underlying {@code cloneReset()} throws {@link CloneNotSupportedException}.
     *
     * <p>An anonymous subclass overrides {@code cloneReset()} to always throw
     * {@link CloneNotSupportedException}, simulating a failure that cannot occur on the
     * normal {@code StringTokenizer} code path (because {@code StringTokenizer} implements
     * {@link Cloneable}). The test confirms that {@code clone()} catches the exception and
     * returns {@code null} rather than letting it escape.
     */
    @Test
    void testCloneNotSupportedException() {
        final StringTokenizer tokenizerThatCannotBeCloned = new StringTokenizer() {
            @Override
            Object cloneReset() throws CloneNotSupportedException {
                throw new CloneNotSupportedException("simulated failure");
            }
        };

        final Object cloneResult = tokenizerThatCannotBeCloned.clone();

        assertNull(cloneResult, "clone() should return null when cloneReset() throws CloneNotSupportedException");
    }
}
