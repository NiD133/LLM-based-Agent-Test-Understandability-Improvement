package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test16 extends PercentCodec_ESTest_scaffolding {

    /**
     * Verifies that a single non-ASCII byte is percent-encoded correctly.
     *
     * The input byte -59 is 0xC5 in unsigned form (197 decimal).
     * The default PercentCodec encodes all non-ASCII bytes using percent-encoding,
     * so 0xC5 becomes the three-byte sequence: '%', 'C', '5'
     * which is { 37, 67, 53 } in ASCII values.
     */
    @Test(timeout = 4000)
    public void test16() throws Throwable {
        PercentCodec codec = new PercentCodec();

        // -59 as a signed byte equals 0xC5 (197) unsigned — a non-ASCII character
        byte[] inputBytes = new byte[] { (byte) (-59) };

        byte[] encodedBytes = codec.encode(inputBytes);

        // '%' = 37, 'C' = 67, '5' = 53  →  the percent-encoded form "%C5"
        byte[] expectedPercentC5 = new byte[] { (byte) 37, (byte) 67, (byte) 53 };
        assertArrayEquals(expectedPercentC5, encodedBytes);
    }
}
