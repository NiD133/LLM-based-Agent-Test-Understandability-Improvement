package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test01 extends CharSetUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testSqueezeRemovesConsecutiveDuplicateCharsInCharSet() throws Throwable {
        // A 4-slot array where only slot 1 holds the active char-set definition.
        // "ZS[4!;6>G|3UPaJfj" lists individual characters to squeeze, including 'f'.
        String[] charSetSpecs = new String[4];
        charSetSpecs[1] = "ZS[4!;6>G|3UPaJfj";

        // "offset" contains consecutive 'f' chars; since 'f' is in the char set,
        // the duplicate 'f' is squeezed away, yielding "ofset" in the result.
        String result = CharSetUtils.squeeze("Minimum abbreviation width with offset is %d", charSetSpecs);

        assertEquals("Minimum abbreviation width with ofset is %d", result);
    }
}
