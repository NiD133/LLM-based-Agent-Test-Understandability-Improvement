package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test17 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        byte[] bytesToAlwaysEncodeAndEncode = new byte[5];
        PercentCodec codec = new PercentCodec(bytesToAlwaysEncodeAndEncode, false);

        Object encodedBytes = codec.encode((Object) bytesToAlwaysEncodeAndEncode);
        Object decodedBytes = codec.decode(encodedBytes);

        assertNotNull(decodedBytes);
        assertNotSame(bytesToAlwaysEncodeAndEncode, encodedBytes);
    }
}
