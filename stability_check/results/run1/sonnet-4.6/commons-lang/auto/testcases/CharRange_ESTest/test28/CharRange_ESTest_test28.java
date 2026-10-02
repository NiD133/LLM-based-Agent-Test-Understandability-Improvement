package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharRange_ESTest_test28 extends CharRange_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_hashCode_doesNotThrow_andPropertiesMatchSingleCharRange() throws Throwable {
        // A single-character range isIn('O','O') should have start == end == 'O' and be non-negated
        CharRange singleCharRange = CharRange.isIn('O', 'O');

        // hashCode() must not throw
        singleCharRange.hashCode();

        assertEquals('O', singleCharRange.getStart());
        assertEquals('O', singleCharRange.getEnd());
        assertFalse(singleCharRange.isNegated());
    }
}
