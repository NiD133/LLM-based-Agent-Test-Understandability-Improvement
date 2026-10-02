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

    // Each zero byte encodes to two hex characters "00", so two zero bytes produce "0000".
    @Test(timeout = 4000)
    public void test04_encodeToString_twoBytesAllZeros_returnsUpperCaseHexString() throws Throwable {
        Base16 upperCaseBase16 = new Base16(false);
        byte[] twoBytesAllZeros = new byte[2];
        String encodedHex = upperCaseBase16.encodeToString(twoBytesAllZeros);
        assertEquals("0000", encodedHex);
    }
}
