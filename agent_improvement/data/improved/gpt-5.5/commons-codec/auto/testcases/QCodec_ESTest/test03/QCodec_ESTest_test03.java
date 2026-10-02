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
public class QCodec_ESTest_test03 extends QCodec_ESTest_scaffolding {

    private static final String TEXT_WITH_BLANKS = "=rcy4cI]MK] ]-";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        QCodec codec = new QCodec();

        codec.setEncodeBlanks(true);
        codec.encode(TEXT_WITH_BLANKS);

        assertTrue(codec.isEncodeBlanks());
    }
}
