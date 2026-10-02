package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test00 extends CharRange_ESTest_scaffolding {

    /**
     * A negated range built from 'G' to 'j' should render as the range
     * "G-j" prefixed with '^' to mark the negation. Calling toString twice
     * also verifies that the cached representation stays consistent.
     */
    @Test(timeout = 4000)
    public void negatedRange_toString_isCaretStartDashEnd() throws Throwable {
        CharRange negatedRange = CharRange.isNotIn('G', 'j');

        negatedRange.toString();
        String representation = negatedRange.toString();

        assertNotNull(representation);
        assertEquals("^G-j", representation);
    }
}
