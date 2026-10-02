package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test06 extends URLCodec_ESTest_scaffolding {

    /**
     * Encoding a null object should return null rather than throw,
     * as specified by {@link URLCodec#encode(Object)}.
     */
    @Test(timeout = 4000)
    public void encodeNullObjectReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec();

        Object encoded = urlCodec.encode((Object) null);

        assertNull(encoded);
    }
}
