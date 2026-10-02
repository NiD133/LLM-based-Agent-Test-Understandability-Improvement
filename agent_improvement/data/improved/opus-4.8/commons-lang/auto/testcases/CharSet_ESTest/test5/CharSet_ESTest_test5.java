package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test5 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSet#getInstance(String...)} returns a non-null
     * instance when given a definition that combines two character ranges
     * ("A-X" and "a-z") in a single string.
     */
    @Test(timeout = 4000)
    public void getInstanceWithCombinedRangesReturnsInstance() throws Throwable {
        String[] rangeDefinitions = { "A-Xa-z" };

        CharSet charSet = CharSet.getInstance(rangeDefinitions);

        assertNotNull(charSet);
    }
}
