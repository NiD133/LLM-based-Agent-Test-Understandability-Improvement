package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test11 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        byte[] alwaysEncodeZeroByte = new byte[1];
        PercentCodec codec = new PercentCodec(alwaysEncodeZeroByte, false);

        byte[] encodedZeroByte = codec.encode(alwaysEncodeZeroByte);
        byte[] decodedZeroByte = codec.decode(encodedZeroByte);

        assertArrayEquals(new byte[] { (byte) 37, (byte) 48, (byte) 48 }, encodedZeroByte);
        assertArrayEquals(new byte[] { (byte) 0 }, decodedZeroByte);
    }
}
