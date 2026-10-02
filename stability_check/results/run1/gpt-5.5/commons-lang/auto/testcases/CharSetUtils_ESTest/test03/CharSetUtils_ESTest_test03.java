package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test03 extends CharSetUtils_ESTest_scaffolding {

    private static final String TEXT_WITH_REPEATED_F = "offset cannot be negative";
    private static final String TEXT_AFTER_SQUEEZING_REPEATED_F = "ofset cannot be negative";
    private static final String CHARACTER_SET_CONTAINING_F = "ZS[4!;6>G|3UPaJfj";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        String[] characterSets = new String[2];
        characterSets[1] = CHARACTER_SET_CONTAINING_F;

        String squeezedText = CharSetUtils.squeeze(TEXT_WITH_REPEATED_F, characterSets);

        assertEquals(TEXT_AFTER_SQUEEZING_REPEATED_F, squeezedText);
    }
}
