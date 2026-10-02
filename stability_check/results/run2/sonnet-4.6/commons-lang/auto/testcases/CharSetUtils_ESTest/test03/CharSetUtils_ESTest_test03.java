package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test03 extends CharSetUtils_ESTest_scaffolding {

    // Verifies that squeeze collapses consecutive repeated characters that belong to the given set.
    // The char-set array has a null at index 0 (treated as empty by CharSet) and the literal
    // character set "ZS[4!;6>G|3UPaJfj" at index 1, which includes 'f'.
    // "offset" contains "ff", so squeeze reduces it to "f", yielding "ofset cannot be negative".
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        String[] charSet = new String[2];
        charSet[1] = "ZS[4!;6>G|3UPaJfj";  // charSet[0] is null, effectively ignored

        String squeezed = CharSetUtils.squeeze("offset cannot be negative", charSet);

        assertEquals("ofset cannot be negative", squeezed);
    }
}
