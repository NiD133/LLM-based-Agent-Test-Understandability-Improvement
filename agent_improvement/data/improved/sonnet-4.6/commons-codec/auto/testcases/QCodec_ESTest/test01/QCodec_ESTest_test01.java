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
public class QCodec_ESTest_test01 extends QCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void encodingNullObjectReturnsNull() throws Throwable {
        QCodec codec = new QCodec();
        Object result = codec.encode((Object) null);
        assertNull(result);
    }
}
