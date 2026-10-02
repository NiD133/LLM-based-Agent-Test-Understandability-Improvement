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
public class URLCodec_ESTest_test13 extends URLCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test13() throws Throwable {
        byte[] byteArray0 = new byte[8];
        byteArray0[0] = (byte) (-38);
        byte[] byteArray1 = URLCodec.encodeUrl((BitSet) null, byteArray0);
        assertEquals(24, byteArray1.length);
    }
}
