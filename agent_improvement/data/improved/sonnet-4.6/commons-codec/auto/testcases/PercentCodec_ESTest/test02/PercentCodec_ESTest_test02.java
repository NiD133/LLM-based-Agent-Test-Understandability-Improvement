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
     * Verifies that constructing a PercentCodec with a negative byte in the
     * alwaysEncodeChars array throws an IllegalArgumentException, because only
     * non-negative US-ASCII byte values (0–127) are valid always-encode characters.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // -43 is a negative byte value, which is invalid for alwaysEncodeChars
        byte[] alwaysEncodeCharsWithNegativeByte = new byte[] { (byte) (-43) };

        try {
            new PercentCodec(alwaysEncodeCharsWithNegativeByte, false);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // byte must be >= 0
            //
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
