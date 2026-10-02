package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test16 extends PercentCodec_ESTest_scaffolding {

    /**
     * A non US-ASCII byte (here 0xC5, stored as the signed value -59) must be
     * percent-encoded. The codec emits the escape character '%' (37) followed by
     * the two upper-case hex digits 'C' (67) and '5' (53), i.e. the bytes for "%C5".
     */
    @Test(timeout = 4000)
    public void encodeNonAsciiBytePercentEncodesItAsHex() throws Throwable {
        PercentCodec percentCodec = new PercentCodec();

        byte[] nonAsciiInput = new byte[] { (byte) -59 };
        byte[] encoded = percentCodec.encode(nonAsciiInput);

        byte[] expected = new byte[] { (byte) '%', (byte) 'C', (byte) '5' };
        assertArrayEquals(expected, encoded);
    }
}
