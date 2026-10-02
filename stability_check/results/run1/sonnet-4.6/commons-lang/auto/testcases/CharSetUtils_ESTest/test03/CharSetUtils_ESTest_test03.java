package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test03 extends CharSetUtils_ESTest_scaffolding {

    /**
     * Verifies that squeeze() removes consecutive duplicate characters that belong to the
     * specified character set. The set array has a null first element (ignored by deepEmpty)
     * and a non-null second element "ZS[4!;6>G|3UPaJfj" that includes 'f'.
     * "offset" contains two consecutive 'f' characters; after squeezing they collapse to one,
     * yielding "ofset cannot be negative".
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Index 0 is null (default); index 1 defines the squeeze character set containing 'f'
        String[] charSet = new String[2];
        charSet[1] = "ZS[4!;6>G|3UPaJfj";

        // The double 'f' in "offset" is in the set, so squeeze collapses it to a single 'f'
        String squeezed = CharSetUtils.squeeze("offset cannot be negative", charSet);

        assertEquals("ofset cannot be negative", squeezed);
    }
}
