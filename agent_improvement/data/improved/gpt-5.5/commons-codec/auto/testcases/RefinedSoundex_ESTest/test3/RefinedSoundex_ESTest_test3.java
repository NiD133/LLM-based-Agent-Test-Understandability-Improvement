package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class RefinedSoundex_ESTest_test3 extends RefinedSoundex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test3() throws Throwable {
        RefinedSoundex refinedSoundex = new RefinedSoundex();
        Object nonStringInput = new Object();

        try {
            refinedSoundex.encode(nonStringInput);
            fail("Expected RefinedSoundex.encode(Object) to reject a non-String input");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.language.RefinedSoundex", e);
        }
    }
}
