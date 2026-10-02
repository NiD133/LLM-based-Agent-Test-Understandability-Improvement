package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test13 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        Base16 base16_0 = new Base16(false);
        boolean boolean0 = base16_0.isInAlphabet((byte) 10);
        assertFalse(boolean0);
    }
}
