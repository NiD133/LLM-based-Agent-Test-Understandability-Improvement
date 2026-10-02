package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Base16_ESTest_test08 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test08() throws Throwable {
        Base16 uppercaseBase16 = new Base16(false);
        byte[] input = new byte[2];

        BaseNCodec.Context context = new BaseNCodec.Context();
        context.ibitWorkArea = (int) (byte) 106;

        int encodeOffsetBeyondInput = 2741;
        int negativeLengthMarksEndOfInput = (-2272);
        uppercaseBase16.encode(input, encodeOffsetBeyondInput, negativeLengthMarksEndOfInput, context);

        int decodeOffset = (int) (byte) 85;
        int decodeLength = (int) (byte) 85;
        uppercaseBase16.decode(input, decodeOffset, decodeLength, context);

        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
