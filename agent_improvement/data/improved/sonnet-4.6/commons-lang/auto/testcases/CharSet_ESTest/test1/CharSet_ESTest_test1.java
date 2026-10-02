package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Set;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSet_ESTest_test1 extends CharSet_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_charSetEqualsItself_returnsTrue() throws Throwable {
        // A one-element array with a null entry produces a CharSet that is not a
        // cached COMMON entry, so getInstance constructs a fresh instance.
        String[] singleNullEntry = new String[1];
        CharSet charSet = CharSet.getInstance(singleNullEntry);

        // A CharSet must be reflexively equal to itself (equals contract).
        boolean isEqualToSelf = charSet.equals(charSet);
        assertTrue("A CharSet must be equal to itself", isEqualToSelf);
    }
}
