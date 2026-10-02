package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test05 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range should be reflexively equal to itself,
     * report that it is negated, and expose the same character as both its
     * start and end bound.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        final char pipe = '|';
        CharRange negatedPipeRange = CharRange.isNot(pipe);

        assertTrue("a range must be equal to itself", negatedPipeRange.equals(negatedPipeRange));
        assertTrue("isNot(...) creates a negated range", negatedPipeRange.isNegated());
        assertEquals("single-char range starts at the character", pipe, negatedPipeRange.getStart());
        assertEquals("single-char range ends at the character", pipe, negatedPipeRange.getEnd());
    }
}
