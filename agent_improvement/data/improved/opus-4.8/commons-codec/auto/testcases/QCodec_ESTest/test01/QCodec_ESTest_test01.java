package org.apache.commons.codec.net;

import static org.junit.Assert.assertNull;

import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test01 extends QCodec_ESTest_scaffolding {

    /**
     * Encoding a {@code null} object should return {@code null} rather than
     * throwing or producing an encoded value.
     */
    @Test(timeout = 4000)
    public void encodeNullObjectReturnsNull() throws Throwable {
        QCodec qCodec = new QCodec();

        Object encoded = qCodec.encode((Object) null);

        assertNull("Encoding a null object should yield null", encoded);
    }
}
