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

    // A negative length signals EOF to Base58.encode; with no previously accumulated
    // data the codec returns an empty byte array.
    private static final int NEGATIVE_OFFSET = -20;
    private static final int NEGATIVE_LENGTH_SIGNALS_EOF = -20;

    @Test(timeout = 4000)
    public void test02_encodeWithNegativeLengthSignalsEofAndReturnsEmptyArray() throws Throwable {
        Base58 base58 = new Base58();
        byte[] inputData = new byte[1];

        byte[] result = base58.encode(inputData, NEGATIVE_OFFSET, NEGATIVE_LENGTH_SIGNALS_EOF);

        assertEquals(0, result.length);
    }
}
