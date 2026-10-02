package org.apache.commons.lang3;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class CharSetUtils_ESTest_test05 extends CharSetUtils_ESTest_scaffolding {

    /**
     * When the character set contains only empty/null entries, {@code keep} treats
     * the set as empty and keeps nothing, returning an empty string regardless of
     * the input characters.
     */
    @Test(timeout = 4000)
    public void keepWithEffectivelyEmptySetReturnsEmptyString() throws Throwable {
        String inputToFilter = "=11X|n;V_";
        String[] emptyCharacterSet = new String[1]; // single null entry => effectively empty set

        String kept = CharSetUtils.keep(inputToFilter, emptyCharacterSet);

        assertEquals("", kept);
    }
}
