package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base58_ESTest_test02 extends Base58_ESTest_scaffolding {

    private static final int NEGATIVE_EOF_MARKER = (int) (byte) (-20);

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Base58 base58 = new Base58();
        byte[] singleZeroByte = new byte[1];

        byte[] encodedBytes = base58.encode(singleZeroByte, NEGATIVE_EOF_MARKER, NEGATIVE_EOF_MARKER);

        assertEquals(0, encodedBytes.length);
    }
}
