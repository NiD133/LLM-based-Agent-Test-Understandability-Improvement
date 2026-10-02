package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class PercentCodec_ESTest_test00 extends PercentCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        byte[] alwaysEncodeNullByte = new byte[1];
        PercentCodec codecUsingPlusForSpace = new PercentCodec(alwaysEncodeNullByte, true);

        byte[] inputEndingWithIncompletePercentEscape = new byte[9];
        inputEndingWithIncompletePercentEscape[8] = (byte) 37;

        try {
            codecUsingPlusForSpace.decode(inputEndingWithIncompletePercentEscape);
            fail("Expecting exception: Exception");
        } catch (Exception e) {
            //
            // Invalid percent decoding:
            //
            verifyException("org.apache.commons.codec.net.PercentCodec", e);
        }
    }
}
