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
        final RefinedSoundex encoder = new RefinedSoundex();
        final Object nonStringInput = new Object();

        try {
            encoder.encode(nonStringInput);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            verifyException("org.apache.commons.codec.language.RefinedSoundex", e);
        }
    }
}
