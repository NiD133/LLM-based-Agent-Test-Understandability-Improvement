package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Hex_ESTest_test16 extends Hex_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test16() throws Throwable {
        byte[] sourceBytes = new byte[4];
        char[] encodedOutput = new char[2];

        int sourceOffsetBeyondArray = 422;
        int bytesToEncode = 0;
        boolean useLowerCaseDigits = true;
        int outputOffsetBeforeArray = -435;

        Hex.encodeHex(
                sourceBytes,
                sourceOffsetBeyondArray,
                bytesToEncode,
                useLowerCaseDigits,
                encodedOutput,
                outputOffsetBeforeArray);

        assertEquals(2, encodedOutput.length);
    }
}
