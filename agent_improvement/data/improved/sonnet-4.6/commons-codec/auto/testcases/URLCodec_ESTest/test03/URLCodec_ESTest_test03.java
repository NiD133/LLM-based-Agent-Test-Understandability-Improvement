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
public class URLCodec_ESTest_test03 extends URLCodec_ESTest_scaffolding {

    // encode(null, null) should return null without throwing an exception
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        URLCodec codec = new URLCodec();
        String nullInput = null;
        String nullCharset = null;
        String result = codec.encode(nullInput, nullCharset);
        assertNull(result);
    }
}
