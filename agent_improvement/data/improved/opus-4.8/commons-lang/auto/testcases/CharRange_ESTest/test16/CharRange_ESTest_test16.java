package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test16 extends CharRange_ESTest_scaffolding {

    /**
     * A negated single-character range built with {@link CharRange#isNot(char)}
     * should report itself as fully contained within itself.
     */
    @Test(timeout = 4000)
    public void negatedRangeContainsItself() throws Throwable {
        CharRange notOne = CharRange.isNot('1');

        boolean containsItself = notOne.contains(notOne);

        assertTrue("A range must contain itself", containsItself);
        assertEquals("Start of isNot('1') should be '1'", '1', notOne.getStart());
        assertEquals("End of isNot('1') should be '1'", '1', notOne.getEnd());
        assertTrue("isNot(...) must produce a negated range", notOne.isNegated());
    }
}
