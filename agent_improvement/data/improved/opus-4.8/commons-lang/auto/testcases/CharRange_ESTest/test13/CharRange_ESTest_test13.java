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
     * A negated range does NOT contain another negated range unless the outer
     * range's bounds sit inside the inner range's bounds (see CharRange.contains:
     * for two negated ranges it returns start >= range.start && end <= range.end).
     *
     * Here the wider range [", i] negated is checked for containing the narrower
     * single-char range [", "] negated, which is false.
     */
    @Test(timeout = 4000)
    public void containsReturnsFalseForWiderNegatedRangeOverNarrowerNegatedRange() throws Throwable {
        // Negated single-character range over the double-quote character.
        CharRange singleCharNegated = CharRange.isNotIn('"', '"');

        // Negated range; isNotIn reverses out-of-order bounds, so 'i'..'"' becomes start='"', end='i'.
        CharRange spanningNegated = CharRange.isNotIn('i', '"');

        boolean contains = spanningNegated.contains(singleCharNegated);

        assertEquals('i', spanningNegated.getEnd());
        assertEquals('"', spanningNegated.getStart());
        assertFalse(contains);
    }
}
