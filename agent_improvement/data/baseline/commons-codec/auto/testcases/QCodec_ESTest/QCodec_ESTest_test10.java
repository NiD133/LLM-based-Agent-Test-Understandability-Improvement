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
public class QCodec_ESTest_test10 extends QCodec_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        QCodec qCodec0 = new QCodec();
        Object object0 = qCodec0.decode((Object) "=?UTF-8?Q?=3Drcy4cI]MK] ]-?=");
        assertEquals("=rcy4cI]MK] ]-", object0);
    }
}
