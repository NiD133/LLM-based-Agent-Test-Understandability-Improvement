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

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        byte[] byteToAlwaysEncode = new byte[1];
        PercentCodec codec = new PercentCodec(byteToAlwaysEncode, false);

        byte[] encodedNullByte = codec.encode(byteToAlwaysEncode);
        byte[] encodedPercentSequence = codec.encode(encodedNullByte);

        assertArrayEquals(new byte[] { (byte) 37, (byte) 50, (byte) 53, (byte) 48, (byte) 48 }, encodedPercentSequence);
    }
}
