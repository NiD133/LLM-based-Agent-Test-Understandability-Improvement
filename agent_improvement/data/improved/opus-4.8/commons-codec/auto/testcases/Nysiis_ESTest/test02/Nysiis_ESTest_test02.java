package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test02 extends Nysiis_ESTest_scaffolding {

    /**
     * The no-argument constructor should create an encoder configured for strict
     * mode, and encoding a value must not change that configuration.
     */
    @Test(timeout = 4000)
    public void defaultEncoderIsStrictAfterEncoding() throws Throwable {
        Nysiis defaultEncoder = new Nysiis();

        defaultEncoder.encode("A");

        assertTrue("Default Nysiis encoder should be in strict mode", defaultEncoder.isStrict());
    }
}
