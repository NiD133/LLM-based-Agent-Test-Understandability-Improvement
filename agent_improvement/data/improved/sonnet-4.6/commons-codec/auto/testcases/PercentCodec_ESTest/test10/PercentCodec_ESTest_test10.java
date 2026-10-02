package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test10 extends PercentCodec_ESTest_scaffolding {

    // Decoding bytes that contain no percent-escape sequences ('%') and no '+' signs
    // should return the bytes unchanged, regardless of which chars are in the always-encode set.
    @Test(timeout = 4000)
    public void test_decodeNullBytes_returnsUnchangedBytes() throws Throwable {
        byte[] zeroBytesInput = new byte[5]; // all zeros; '0x00' is not '%' or '+'
        PercentCodec codecWithZeroAlwaysEncodeChars = new PercentCodec(zeroBytesInput, false);
        byte[] decodedBytes = codecWithZeroAlwaysEncodeChars.decode(zeroBytesInput);
        assertArrayEquals(new byte[] { (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0 }, decodedBytes);
    }
}
