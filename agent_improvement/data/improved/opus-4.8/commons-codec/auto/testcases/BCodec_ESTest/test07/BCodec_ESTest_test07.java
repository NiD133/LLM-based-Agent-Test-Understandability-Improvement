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
public class BCodec_ESTest_test07 extends BCodec_ESTest_scaffolding {

    /**
     * Decoding an empty byte array should yield an empty byte array,
     * since there is no Base64 content to convert back.
     */
    @Test(timeout = 4000)
    public void doDecoding_withEmptyInput_returnsEmptyArray() throws Throwable {
        BCodec bCodec = new BCodec();
        byte[] emptyInput = new byte[0];

        byte[] decoded = bCodec.doDecoding(emptyInput);

        assertArrayEquals(new byte[] {}, decoded);
    }
}
