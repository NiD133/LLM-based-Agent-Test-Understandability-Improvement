package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test02 extends QCodec_ESTest_scaffolding {

    /**
     * Encoding an already-encoded RFC 1522 header should escape its special
     * characters again. The "=", "?" and other reserved characters in the input
     * are themselves quoted (e.g. "=" becomes "=3D", "?" becomes "=3F"),
     * producing a new, doubly-encoded "=?charset?Q?...?=" word.
     */
    @Test(timeout = 4000)
    public void encodeReencodesAnAlreadyEncodedHeaderString() throws Throwable {
        QCodec qCodec = new QCodec();

        String input = "=?UTF-8?Q?=3Drcy4cI]MK] ]-?=";
        Object encoded = qCodec.encode((Object) input);

        String expected = "=?UTF-8?Q?=3D=3FUTF-8=3FQ=3F=3D3Drcy4cI]MK] ]-=3F=3D?=";
        assertNotNull(encoded);
        assertEquals(expected, encoded);
    }
}
