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
     * Verifies that the default Nysiis constructor creates an encoder in strict mode
     * (encoded strings are limited to a maximum length of 6 characters).
     * Encoding a single-character input exercises the encoder before checking the mode.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Nysiis encoder = new Nysiis();
        encoder.encode("A");
        assertTrue(encoder.isStrict());
    }
}
