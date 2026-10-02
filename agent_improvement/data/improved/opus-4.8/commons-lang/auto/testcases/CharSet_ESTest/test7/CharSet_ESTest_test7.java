package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test7 extends CharSet_ESTest_scaffolding {

    /**
     * A CharSet built from a single null definition string contains no
     * characters, so {@code contains} returns false for any character.
     */
    @Test(timeout = 4000)
    public void contains_returnsFalse_whenCharSetBuiltFromNullDefinition() throws Throwable {
        String[] definitionsWithSingleNull = new String[1];
        CharSet emptyCharSet = CharSet.getInstance(definitionsWithSingleNull);

        boolean containsV = emptyCharSet.contains('v');

        assertFalse(containsV);
    }
}
