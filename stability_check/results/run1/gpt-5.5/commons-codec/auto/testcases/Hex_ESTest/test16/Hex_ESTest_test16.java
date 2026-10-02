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
        final byte[] sourceBytes = new byte[4];
        final char[] outputBuffer = new char[2];

        final int ignoredSourceOffset = 422;
        final int zeroBytesToEncode = 0;
        final boolean useLowerCaseDigits = true;
        final int ignoredOutputOffset = -435;

        Hex.encodeHex(sourceBytes, ignoredSourceOffset, zeroBytesToEncode,
                useLowerCaseDigits, outputBuffer, ignoredOutputOffset);

        assertEquals(2, outputBuffer.length);
    }
}
