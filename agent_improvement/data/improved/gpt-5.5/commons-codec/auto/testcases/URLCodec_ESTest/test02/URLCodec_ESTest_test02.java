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
public class URLCodec_ESTest_test02 extends URLCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        URLCodec codec = new URLCodec();
        String encodedValue = "*aAC%25%2B";

        String decodedValue = codec.decode(encodedValue);

        assertNotNull(decodedValue);
        assertEquals("*aAC%+", decodedValue);
    }
}
