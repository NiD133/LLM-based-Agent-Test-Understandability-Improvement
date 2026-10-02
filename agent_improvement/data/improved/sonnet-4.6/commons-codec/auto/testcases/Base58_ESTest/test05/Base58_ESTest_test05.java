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

    // Base58 "Dt2" decodes to two bytes: 0xA9 (169 unsigned, -87 signed) and 0x3F (63)
    private static final byte[] EXPECTED_BYTES_FOR_DT2 = { (byte) (-87), (byte) 63 };

    @Test(timeout = 4000)
    public void test05_decodeBase58StringProducesCorrectBytes() throws Throwable {
        Base58 codec = new Base58();

        byte[] decodedBytes = codec.decode("Dt2");

        assertArrayEquals(EXPECTED_BYTES_FOR_DT2, decodedBytes);
    }
}
