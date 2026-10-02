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
public class QCodec_ESTest_test06 extends QCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06_doDecoding_nullInput_returnsNull() throws Throwable {
        QCodec codec = new QCodec();
        // doDecoding should handle null input gracefully by returning null
        byte[] result = codec.doDecoding((byte[]) null);
        assertNull(result);
    }
}
