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
public class BCodec_ESTest_test05 extends BCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding a null byte array returns null rather than
     * throwing, as documented by {@link BCodec#doEncoding(byte[])}.
     */
    @Test(timeout = 4000)
    public void doEncoding_withNullInput_returnsNull() throws Throwable {
        BCodec bCodec = new BCodec();

        byte[] encoded = bCodec.doEncoding((byte[]) null);

        assertNull(encoded);
    }
}
