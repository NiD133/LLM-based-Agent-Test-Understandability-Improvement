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

    // doEncoding(null) must return null — the codec treats a null input as a no-op
    @Test(timeout = 4000)
    public void test_doEncoding_returnsNull_whenInputIsNull() throws Throwable {
        QCodec qCodec0 = new QCodec();
        byte[] result = qCodec0.doEncoding((byte[]) null);
        assertNull(result);
    }
}
