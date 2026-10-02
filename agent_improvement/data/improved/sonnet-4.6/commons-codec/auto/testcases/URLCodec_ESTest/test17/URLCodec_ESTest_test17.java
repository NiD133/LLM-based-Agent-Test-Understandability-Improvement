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
public class URLCodec_ESTest_test17 extends URLCodec_ESTest_scaffolding {

    // URLCodec.decodeUrl returns null when given a null input (null-in, null-out contract)
    @Test(timeout = 4000)
    public void test_decodeUrl_returnsNullForNullInput() throws Throwable {
        byte[] decodedBytes = URLCodec.decodeUrl((byte[]) null);
        assertNull(decodedBytes);
    }
}
