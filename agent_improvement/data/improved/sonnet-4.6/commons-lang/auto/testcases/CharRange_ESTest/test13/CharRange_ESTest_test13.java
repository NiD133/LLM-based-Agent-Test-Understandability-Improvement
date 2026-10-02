package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test13 extends CharRange_ESTest_scaffolding {

    /**
     * Verifies that isNotIn normalizes reversed start/end arguments and that a
     * negated range excluding ['"','i'] does not contain a negated range
     * excluding only ['"','"'].
     *
     * When arguments to isNotIn are in reversed order ('i' > '"'), the
     * constructor swaps them so that start <= end. Then, for two negated
     * ranges, containment requires the argument range's excluded interval to
     * be a superset of this range's excluded interval; since ['"','"'] is
     * smaller than ['"','i'], contains() returns false.
     */
    @Test(timeout = 4000)
    public void test13() throws Throwable {
        // Negated range that excludes only the double-quote character
        CharRange notInDoubleQuoteOnly = CharRange.isNotIn('"', '"');

        // Negated range created with reversed args ('i' > '"'); constructor normalizes to start='"', end='i'
        CharRange notInDoubleQuoteToI = CharRange.isNotIn('i', '"');

        // Verify normalization: reversed arguments are swapped so start <= end
        assertEquals('"', notInDoubleQuoteToI.getStart());
        assertEquals('i', notInDoubleQuoteToI.getEnd());

        // notInDoubleQuoteToI excludes ['"','i'] while notInDoubleQuoteOnly excludes ['"','"'];
        // the smaller excluded interval means containment fails
        boolean contained = notInDoubleQuoteToI.contains(notInDoubleQuoteOnly);
        assertFalse(contained);
    }
}
