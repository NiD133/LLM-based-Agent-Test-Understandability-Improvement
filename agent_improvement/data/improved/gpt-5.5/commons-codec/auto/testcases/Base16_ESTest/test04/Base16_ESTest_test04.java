package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test04 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Base16 uppercaseBase16 = new Base16(false);
        byte[] twoZeroBytes = new byte[2];

        String encodedZeros = uppercaseBase16.encodeToString(twoZeroBytes);

        assertEquals("0000", encodedZeros);
    }
}
