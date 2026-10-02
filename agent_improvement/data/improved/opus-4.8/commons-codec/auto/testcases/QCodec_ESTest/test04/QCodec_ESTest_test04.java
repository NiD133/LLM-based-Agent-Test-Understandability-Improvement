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
public class QCodec_ESTest_test04 extends QCodec_ESTest_scaffolding {

    /**
     * Verifies that encoding a null byte array returns null
     * rather than throwing or producing an empty array.
     */
    @Test(timeout = 4000)
    public void doEncodingReturnsNullForNullInput() throws Throwable {
        QCodec qCodec = new QCodec();

        byte[] encoded = qCodec.doEncoding((byte[]) null);

        assertNull("Encoding a null byte array should yield null", encoded);
    }
}
