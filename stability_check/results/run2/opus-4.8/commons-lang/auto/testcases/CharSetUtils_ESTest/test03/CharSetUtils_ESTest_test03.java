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
     * squeeze collapses runs of repeated characters that belong to the given set.
     * The set string "ZS[4!;6>G|3UPaJfj" contains 'f', so the double "ff" in
     * "offset" is squeezed to a single 'f'. All other characters are left as-is.
     */
    @Test(timeout = 4000)
    public void squeezeCollapsesRepeatedCharactersInSet() throws Throwable {
        String[] set = { null, "ZS[4!;6>G|3UPaJfj" };

        String result = CharSetUtils.squeeze("offset cannot be negative", set);

        assertEquals("ofset cannot be negative", result);
    }
}
