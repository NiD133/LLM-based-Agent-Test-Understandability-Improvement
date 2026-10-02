package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test02 extends PercentCodec_ESTest_scaffolding {

    /**
     * The constructor inserts every "always encode" byte into a BitSet, which
     * requires each byte to be non-negative. A negative byte must therefore be
     * rejected with an IllegalArgumentException ("byte must be >= 0").
     */
    @Test(timeout = 4000)
    public void constructorRejectsNegativeAlwaysEncodeByte() throws Throwable {
        byte[] alwaysEncodeChars = new byte[] { (byte) -43 };

        try {
            new PercentCodec(alwaysEncodeChars, false);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Negative bytes cannot be stored in the BitSet: "byte must be >= 0".
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
