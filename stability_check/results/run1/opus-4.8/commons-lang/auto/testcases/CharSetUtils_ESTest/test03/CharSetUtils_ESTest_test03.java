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
     * squeeze collapses runs of repeated characters, but only for characters
     * that appear in the supplied set. Here the set includes 'f', so the
     * doubled "ff" in "offset" is squeezed to a single "f", while every other
     * character (none of which repeats) is left untouched.
     */
    @Test(timeout = 4000)
    public void squeezeCollapsesRepeatedCharacterInSet() throws Throwable {
        // A set entry containing 'f' among its characters; the leading null entry is ignored.
        String[] characterSet = new String[2];
        characterSet[1] = "ZS[4!;6>G|3UPaJfj";

        String squeezed = CharSetUtils.squeeze("offset cannot be negative", characterSet);

        assertEquals("ofset cannot be negative", squeezed);
    }
}
