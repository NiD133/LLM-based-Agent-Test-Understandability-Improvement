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
public class URLCodec_ESTest_test05 extends URLCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        URLCodec codec = new URLCodec();
        long[] emptyBitSetWords = new long[2];
        BitSet unsupportedObject = BitSet.valueOf(emptyBitSetWords);

        try {
            codec.encode((Object) unsupportedObject);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Objects of type java.util.BitSet cannot be URL encoded
            //
            verifyException("org.apache.commons.codec.net.URLCodec", e);
        }
    }
}
