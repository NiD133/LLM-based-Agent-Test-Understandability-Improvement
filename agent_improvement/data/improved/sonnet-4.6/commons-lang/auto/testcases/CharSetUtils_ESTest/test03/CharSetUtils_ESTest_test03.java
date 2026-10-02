package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test03 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // The char-set array has a null slot at index 0 (ignored by CharSet) and the
        // actual pattern at index 1. The pattern includes 'f', so the two consecutive
        // 'f' characters in "offset" are squeezed into one, yielding "ofset".
        String[] charSetDefinition = new String[2];
        charSetDefinition[1] = "ZS[4!;6>G|3UPaJfj";

        String result = CharSetUtils.squeeze("offset cannot be negative", charSetDefinition);

        assertEquals("ofset cannot be negative", result);
    }
}
