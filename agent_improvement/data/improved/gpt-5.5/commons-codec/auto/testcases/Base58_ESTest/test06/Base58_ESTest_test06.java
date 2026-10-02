package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test06 extends Base58_ESTest_scaffolding {

    private static final String BASE58_INPUT = "t2";
    private static final byte[] EXPECTED_DECODED_BYTES = new byte[] { (byte) 11, (byte) (-113) };

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Base58 base58 = new Base58();

        byte[] decodedBytes = base58.decode(BASE58_INPUT);

        assertArrayEquals(EXPECTED_DECODED_BYTES, decodedBytes);
    }
}
