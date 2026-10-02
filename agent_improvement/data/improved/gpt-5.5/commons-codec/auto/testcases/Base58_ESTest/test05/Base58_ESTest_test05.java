package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test05 extends Base58_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Base58 codec = new Base58();
        String encodedValue = "Dt2";

        byte[] decodedBytes = codec.decode(encodedValue);

        byte[] expectedDecodedBytes = new byte[] { (byte) (-87), (byte) 63 };
        assertArrayEquals(expectedDecodedBytes, decodedBytes);
    }
}
