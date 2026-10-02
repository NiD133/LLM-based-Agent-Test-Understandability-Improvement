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
     * Verifies that a negative length passed to {@link Base16#encode} marks the
     * encoding context as end-of-file. Once EOF is reached, a subsequent encode
     * call (here also with a negative length) is a no-op and the call returns
     * without touching the data buffer.
     */
    @Test(timeout = 4000)
    public void encodeWithNegativeLengthSignalsEofAndIsIgnoredAfterwards() throws Throwable {
        Base16 base16 = new Base16(false);
        byte[] data = new byte[2];
        BaseNCodec.Context context = new BaseNCodec.Context();

        // First negative length flags EOF on the context.
        base16.encode(data, 1394, -1, context);
        // After EOF, this second call simply returns and does nothing.
        base16.encode(data, 2741, -2272, context);

        assertEquals(64, BaseNCodec.PEM_CHUNK_SIZE);
    }
}
