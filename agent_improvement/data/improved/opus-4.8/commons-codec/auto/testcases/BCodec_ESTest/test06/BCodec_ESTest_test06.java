package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.apache.commons.codec.CodecPolicy;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class BCodec_ESTest_test06 extends BCodec_ESTest_scaffolding {

    /**
     * Verifies that decoding a null byte array returns null,
     * as BCodec.doDecoding short-circuits on null input.
     */
    @Test(timeout = 4000)
    public void doDecodingWithNullInputReturnsNull() throws Throwable {
        BCodec bCodec = new BCodec();

        byte[] decoded = bCodec.doDecoding((byte[]) null);

        assertNull(decoded);
    }
}
