package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test04 extends Nysiis_ESTest_scaffolding {

    /**
     * Encoding a {@code null} input should be handled gracefully (the algorithm
     * returns {@code null} for it), and constructing a Nysiis encoder with the
     * no-argument constructor should leave it in strict mode by default.
     */
    @Test(timeout = 4000)
    public void encodingNullDoesNotAffectDefaultStrictMode() throws Throwable {
        Nysiis defaultEncoder = new Nysiis();

        defaultEncoder.nysiis((String) null);

        assertTrue("The no-arg constructor should enable strict mode by default",
                defaultEncoder.isStrict());
    }
}
