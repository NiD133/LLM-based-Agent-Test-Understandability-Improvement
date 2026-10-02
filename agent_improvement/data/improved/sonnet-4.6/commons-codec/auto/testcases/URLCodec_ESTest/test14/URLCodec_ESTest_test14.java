package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.BitSet;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test14 extends URLCodec_ESTest_scaffolding {

    // URLCodec.encode(byte[]) must propagate null input as a null result,
    // matching the contract of encodeUrl(BitSet, byte[]) which returns null
    // when the byte array argument is null.
    @Test(timeout = 4000)
    public void test_encodingNullByteArrayReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec();
        byte[] result = urlCodec.encode((byte[]) null);
        assertNull(result);
    }
}
