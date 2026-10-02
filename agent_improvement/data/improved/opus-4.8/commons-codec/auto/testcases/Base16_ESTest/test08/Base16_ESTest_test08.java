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

    /**
     * Verifies that encoding with a negative length flags end-of-file on the
     * shared context, and that a subsequent decode on that EOF context returns
     * quietly (no exception) because the default decoding policy is lenient,
     * even though a half-byte (ibitWorkArea) is still pending.
     */
    @Test(timeout = 4000)
    public void test08() throws Throwable {
        // Upper-case Base16 codec using the default (lenient) decoding policy.
        Base16 base16 = new Base16(false);

        byte[] data = new byte[2];

        // Shared codec context with a pending half-byte left in the work area.
        BaseNCodec.Context context = new BaseNCodec.Context();
        context.ibitWorkArea = (byte) 106;

        // Negative length marks the context as EOF; encode performs no work.
        int negativeLength = -2272;
        base16.encode(data, 2741, negativeLength, context);

        // Decoding on an EOF context with a lenient policy returns without error,
        // despite the leftover half-byte in ibitWorkArea.
        base16.decode(data, (byte) 85, (byte) 85, context);

        // Sanity check on the inherited PEM chunk-size constant.
        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
