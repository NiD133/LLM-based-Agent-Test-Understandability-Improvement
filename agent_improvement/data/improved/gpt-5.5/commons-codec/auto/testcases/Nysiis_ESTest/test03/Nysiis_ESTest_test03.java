package org.apache.commons.codec.language;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Nysiis_ESTest_test03 extends Nysiis_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void testDefaultEncoderRemainsStrictAfterEncodingEmptyString() throws Throwable {
        Nysiis defaultEncoder = new Nysiis();

        defaultEncoder.nysiis("");

        assertTrue(defaultEncoder.isStrict());
    }
}
