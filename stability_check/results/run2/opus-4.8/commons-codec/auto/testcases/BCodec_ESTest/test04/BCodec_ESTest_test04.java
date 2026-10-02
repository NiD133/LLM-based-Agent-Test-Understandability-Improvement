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
public class BCodec_ESTest_test04 extends BCodec_ESTest_scaffolding {

    /**
     * Encoding a null object should return null rather than throwing,
     * as specified by BCodec#encode(Object).
     */
    @Test(timeout = 4000)
    public void encodeNullObjectReturnsNull() throws Throwable {
        BCodec bCodec = new BCodec();

        Object encoded = bCodec.encode((Object) null);

        assertNull(encoded);
    }
}
