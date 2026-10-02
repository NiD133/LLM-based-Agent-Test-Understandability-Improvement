package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test4 extends CharSet_ESTest_scaffolding {

    /**
     * Verifies that CharSet.getInstance() returns a non-null instance when given
     * a sparse String array where most entries are null and only index 2 holds a
     * complex pattern (special characters, a negated range, and a plain range).
     */
    @Test(timeout = 4000)
    public void test_getInstance_sparseArrayWithComplexPattern_returnsNonNull() throws Throwable {
        // Build a 6-element array; only index 2 carries a pattern — the rest are null.
        String[] setDefinitions = new String[6];
        setDefinitions[2] = "\"@mi/vnsJ<U6tm^D-O";

        CharSet charSet = CharSet.getInstance(setDefinitions);

        assertNotNull(charSet);
    }
}
