package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test06 extends PercentCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding twice produces a stable, percent-escaped result.
     *
     * <p>The codec is configured to always encode the NUL byte (0x00). On top of
     * that, the constructor always adds the escape character '%' to the
     * always-encode set. So both 0x00 and '%' get percent-encoded.</p>
     *
     * <p>First pass: a single NUL byte {0x00} becomes the ASCII text "%00",
     * i.e. the bytes {'%', '0', '0'}. Second pass: the leading '%' is itself
     * escaped to "%25" while the two '0' characters are left untouched, giving
     * "%2500", i.e. the bytes {'%', '2', '5', '0', '0'}.</p>
     */
    @Test(timeout = 4000)
    public void testEncodeTwiceEscapesPercentSign() throws Throwable {
        // Always-encode the NUL byte (0x00); the constructor also escapes '%'.
        byte[] alwaysEncodeChars = new byte[] { (byte) 0x00 };
        PercentCodec percentCodec = new PercentCodec(alwaysEncodeChars, false);

        // First encoding: {0x00} -> "%00".
        byte[] singleNulByte = new byte[] { (byte) 0x00 };
        byte[] firstEncoding = percentCodec.encode(singleNulByte);
        assertArrayEquals(
                new byte[] { (byte) '%', (byte) '0', (byte) '0' },
                firstEncoding);

        // Second encoding: the '%' in "%00" is escaped, yielding "%2500".
        byte[] secondEncoding = percentCodec.encode(firstEncoding);
        assertArrayEquals(
                new byte[] { (byte) '%', (byte) '2', (byte) '5', (byte) '0', (byte) '0' },
                secondEncoding);
    }
}
