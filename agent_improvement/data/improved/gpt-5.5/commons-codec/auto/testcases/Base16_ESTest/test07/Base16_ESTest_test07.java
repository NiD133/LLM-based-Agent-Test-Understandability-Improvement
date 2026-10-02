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
public class Base16_ESTest_test07 extends Base16_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Base16 codec = new Base16();
        byte[] upperCaseEncodeTable = codec.encodeTable;
        BaseNCodec.Context decodeContext = new BaseNCodec.Context();
        int offsetWithinEncodeTable = 3;
        int numberOfBytesToDecode = 16;

        codec.decode(upperCaseEncodeTable, offsetWithinEncodeTable, numberOfBytesToDecode, decodeContext);

        assertEquals(CodecPolicy.LENIENT, codec.getCodecPolicy());
    }
}
