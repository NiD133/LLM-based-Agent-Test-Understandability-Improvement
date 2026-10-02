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
        Base16 base16 = new Base16(false);
        byte[] inputBytes = new byte[2];

        BaseNCodec.Context context = new BaseNCodec.Context();
        // Set a non-zero ibitWorkArea to simulate a pending half-byte in the decode buffer
        context.ibitWorkArea = 106;

        // Negative length triggers EOF on the context (encode returns early, setting context.eof = true)
        base16.encode(inputBytes, 2741, -2272, context);

        // With context.eof set and ibitWorkArea != 0, decode invokes trailing-character validation;
        // lenient decoding policy (the default) suppresses any exception
        base16.decode(inputBytes, 85, 85, context);

        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
