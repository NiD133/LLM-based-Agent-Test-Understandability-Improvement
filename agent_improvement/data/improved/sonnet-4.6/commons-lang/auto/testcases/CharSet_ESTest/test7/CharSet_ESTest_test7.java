package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test7 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test7() throws Throwable {
        // A single-element array whose only entry is null (default array value)
        // maps to CharSet.EMPTY via the COMMON cache, producing an empty CharSet
        String[] setSpecWithNullEntry = new String[1];
        CharSet emptyCharSet = CharSet.getInstance(setSpecWithNullEntry);

        // An empty CharSet contains no characters, so 'v' must not be present
        assertFalse(emptyCharSet.contains('v'));
    }
}
