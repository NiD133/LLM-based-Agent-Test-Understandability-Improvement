package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test03 extends Nysiis_ESTest_scaffolding {

    /**
     * The no-argument constructor should create an encoder in strict mode, and
     * encoding an empty string should not change that strict-mode setting.
     */
    @Test(timeout = 4000)
    public void defaultEncoderIsStrictAfterEncodingEmptyString() throws Throwable {
        Nysiis nysiisWithDefaults = new Nysiis();

        nysiisWithDefaults.nysiis("");

        assertTrue("Encoder built with the no-arg constructor should be strict",
                nysiisWithDefaults.isStrict());
    }
}
