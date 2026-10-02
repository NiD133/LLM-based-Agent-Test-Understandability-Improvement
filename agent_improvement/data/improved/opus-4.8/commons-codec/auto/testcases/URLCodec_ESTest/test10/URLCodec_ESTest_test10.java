package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class URLCodec_ESTest_test10 extends URLCodec_ESTest_scaffolding {

    /**
     * Decoding a null Object should return null, regardless of the codec's
     * configured charset (see URLCodec.decode(Object)).
     */
    @Test(timeout = 4000)
    public void decodeNullObjectReturnsNull() throws Throwable {
        URLCodec urlCodec = new URLCodec("p-I+PXoM<\"[Z");

        Object decoded = urlCodec.decode((Object) null);

        assertNull(decoded);
    }
}
