package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test06 extends Hex_ESTest_scaffolding {

    /**
     * Verifies that decodeHex throws when the output offset far exceeds the output array length,
     * leaving no room to write the decoded bytes.
     *
     * A 6-char hex input decodes to 3 bytes, but with outOffset=1589 the required range
     * [1589, 1592) lies entirely outside the 7-element output array, so the method must
     * reject the call with "Output array is not large enough to accommodate decoded data."
     */
    @Test(timeout = 4000)
    public void test06_decodeHex_throwsWhenOutputOffsetExceedsArrayBounds() throws Throwable {
        // 6 null hex characters — would decode to 3 bytes if the output had room
        char[] sixNullHexChars = new char[6];
        // output array of only 7 bytes; at offset 1589 there is no space for the 3 decoded bytes
        byte[] sevenByteOutput = new byte[7];
        int outOffsetBeyondArrayEnd = 1589;

        try {
            Hex.decodeHex(sixNullHexChars, sevenByteOutput, outOffsetBeyondArrayEnd);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Output array is not large enough to accommodate decoded data.
            //
            verifyException("org.apache.commons.codec.binary.Hex", e);
        }
    }
}
