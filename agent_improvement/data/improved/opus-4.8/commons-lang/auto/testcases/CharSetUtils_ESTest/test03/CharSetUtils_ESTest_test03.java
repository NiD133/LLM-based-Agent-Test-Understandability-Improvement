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
     * squeeze() collapses runs of repeated characters when the repeated
     * character belongs to the supplied set. The set below contains 'f',
     * so the doubled "ff" in "offset" is squeezed down to a single "f",
     * while every other character (none of which is repeated) is untouched.
     */
    @Test(timeout = 4000)
    public void testSqueezeCollapsesRepeatedCharThatIsInSet() throws Throwable {
        // A null entry in the set is ignored; only "...f..." matters here.
        String[] set = { null, "ZS[4!;6>G|3UPaJfj" };

        String squeezed = CharSetUtils.squeeze("offset cannot be negative", set);

        assertEquals("ofset cannot be negative", squeezed);
    }
}
