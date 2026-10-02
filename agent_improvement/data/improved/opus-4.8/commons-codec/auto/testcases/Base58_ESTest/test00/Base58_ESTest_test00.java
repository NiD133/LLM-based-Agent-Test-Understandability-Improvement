package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test00 extends Base58_ESTest_scaffolding {

    /**
     * Encoding a two-byte input should run without throwing. The first byte is a
     * non-zero value and the second defaults to zero. The resulting Base58 string
     * is not asserted here because EvoSuite flagged the exact value as unstable.
     */
    @Test(timeout = 4000)
    public void encodeAsString_withTwoByteInput_returnsResultWithoutError() throws Throwable {
        Base58 base58 = new Base58();

        byte[] input = new byte[2];
        input[0] = (byte) -115;

        String encoded = base58.encodeAsString(input);

        // Unstable assertion (left intact from original): assertEquals("(jM", encoded);
    }
}
