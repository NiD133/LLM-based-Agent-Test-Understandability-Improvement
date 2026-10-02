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
        final byte[] inputBytes = new byte[4];
        final char[] outputChars = new char[2];
        final int ignoredInputOffset = 422;
        final int zeroBytesToEncode = 0;
        final boolean useLowerCaseHex = true;
        final int ignoredOutputOffset = -435;

        Hex.encodeHex(inputBytes, ignoredInputOffset, zeroBytesToEncode, useLowerCaseHex, outputChars, ignoredOutputOffset);

        assertEquals(2, outputChars.length);
    }
}
