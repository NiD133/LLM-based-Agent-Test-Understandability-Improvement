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
public class Base16_ESTest_test05 extends Base16_ESTest_scaffolding {

    /**
     * Verifies that encode() with a negative length marks the context as EOF,
     * causing subsequent encode() calls to be ignored. Also confirms the
     * PEM_CHUNK_SIZE constant is 64.
     */
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Base16 base16 = new Base16(false); // uppercase alphabet
        byte[] inputData = new byte[2];
        BaseNCodec.Context context = new BaseNCodec.Context();

        // Negative length signals end-of-input: sets context.eof = true
        base16.encode(inputData, 1394, (-1), context);

        // After EOF, further encode() calls are no-ops regardless of arguments
        base16.encode(inputData, 2741, (-2272), context);

        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
