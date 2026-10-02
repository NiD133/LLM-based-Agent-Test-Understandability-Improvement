package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test08 extends QCodec_ESTest_scaffolding {

    /**
     * Decoding a null object should return null rather than throwing,
     * as specified by {@link QCodec#decode(Object)}.
     */
    @Test(timeout = 4000)
    public void decodeNullObjectReturnsNull() throws Throwable {
        QCodec qCodec = new QCodec();

        Object decoded = qCodec.decode((Object) null);

        assertNull(decoded);
    }
}
