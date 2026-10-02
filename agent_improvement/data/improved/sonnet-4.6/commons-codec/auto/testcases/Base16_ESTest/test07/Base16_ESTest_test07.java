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
        // Default Base16 codec uses upper-case alphabet and LENIENT decoding policy
        Base16 codec = new Base16();

        // Use the codec's own encode table as input (valid Base16 hex characters: '0'–'9', 'A'–'F')
        byte[] hexChars = codec.encodeTable;

        // Create a fresh decoding context
        BaseNCodec.Context context = new BaseNCodec.Context();

        // Decode 16 bytes of the hex-character array starting at offset 3 (chars '3' through 'F')
        codec.decode(hexChars, 3, 16, context);

        // Decoding must not alter the codec's policy — it should remain LENIENT (the default)
        assertEquals(CodecPolicy.LENIENT, codec.getCodecPolicy());
    }
}
