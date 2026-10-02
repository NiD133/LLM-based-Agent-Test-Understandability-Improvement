package org.apache.commons.codec.net;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.charset.Charset;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class QCodec_ESTest_test02 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding a string that already contains RFC-1522 Q-encoded header syntax
     * (including '=', '?', and space characters) produces a fully escaped, valid encoded-word.
     * Special characters '=', '?', and space are percent-encoded in the output.
     */
    @Test(timeout = 4000)
    public void test_encodeObject_alreadyEncodedHeaderString_escapesSpecialChars() throws Throwable {
        QCodec qCodec = new QCodec();
        String inputAlreadyEncodedHeader = "=?UTF-8?Q?=3Drcy4cI]MK] ]-?=";

        Object encodedResult = qCodec.encode((Object) inputAlreadyEncodedHeader);

        assertNotNull(encodedResult);
        assertEquals("=?UTF-8?Q?=3D=3FUTF-8=3FQ=3F=3D3Drcy4cI]MK] ]-=3F=3D?=", encodedResult);
    }
}
