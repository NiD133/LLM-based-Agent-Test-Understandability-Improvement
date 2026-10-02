package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test4 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that {@link CharSet#getInstance(String...)} returns a non-null
     * instance when given a multi-element array in which only one element is a
     * non-null set-definition string and the rest are null.
     */
    @Test(timeout = 4000)
    public void getInstance_withArrayContainingSingleNonNullDefinition_returnsInstance() throws Throwable {
        String[] setDefinitions = new String[6];
        setDefinitions[2] = "\"@mi/vnsJ<U6tm^D-O";

        CharSet charSet = CharSet.getInstance(setDefinitions);

        assertNotNull(charSet);
    }
}
