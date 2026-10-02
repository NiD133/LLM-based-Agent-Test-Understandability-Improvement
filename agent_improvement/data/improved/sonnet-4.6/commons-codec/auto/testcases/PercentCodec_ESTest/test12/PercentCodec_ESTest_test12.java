package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test12 extends PercentCodec_ESTest_scaffolding {

    // Verifies that decoding a null byte array returns null (null-safe contract)
    @Test(timeout = 4000)
    public void test_decode_nullInput_returnsNull() throws Throwable {
        PercentCodec codec = new PercentCodec();
        byte[] result = codec.decode((byte[]) null);
        assertNull("decode(null) should return null", result);
    }
}
