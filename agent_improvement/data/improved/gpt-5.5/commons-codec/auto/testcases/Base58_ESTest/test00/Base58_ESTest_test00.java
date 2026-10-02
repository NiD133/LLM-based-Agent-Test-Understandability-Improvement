package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test00 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Base58 codec = new Base58();
        byte[] bytesToEncode = new byte[2];
        bytesToEncode[0] = (byte) (-115);

        String encoded = codec.encodeAsString(bytesToEncode);
        //  // Unstable assertion: assertEquals("(jM", encoded);
    }
}
