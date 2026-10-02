package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test3 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that the protected varargs constructor accepts a String array that
     * contains both null and non-null elements. The constructor merges each element
     * into the set, skipping null entries, so it must complete without throwing.
     */
    @Test(timeout = 4000)
    public void constructorAcceptsArrayWithNullAndNonNullDefinitions() throws Throwable {
        // Array of six slots; only one slot holds an actual set-definition string,
        // the remaining five stay null and are ignored during merging.
        String[] setDefinitions = new String[6];
        setDefinitions[4] = "=]w9^0fV";

        CharSet charSet = new CharSet(setDefinitions);

        assertNotNull(charSet);
    }
}
