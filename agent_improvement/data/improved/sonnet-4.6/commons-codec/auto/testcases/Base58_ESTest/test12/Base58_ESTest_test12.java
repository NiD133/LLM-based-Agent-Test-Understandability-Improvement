package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test12 extends Base58_ESTest_scaffolding {

    // In Base58, each leading zero byte is represented by the character '1' (the first symbol in the alphabet).
    @Test(timeout = 4000)
    public void test_encodingAllZeroBytes_producesLeadingOnesInBase58() throws Throwable {
        Base58 codec = new Base58();
        byte[] eighteenZeroBytes = new byte[18];

        String encoded = codec.encodeAsString(eighteenZeroBytes);

        assertEquals("111111111111111111", encoded);
    }
}
