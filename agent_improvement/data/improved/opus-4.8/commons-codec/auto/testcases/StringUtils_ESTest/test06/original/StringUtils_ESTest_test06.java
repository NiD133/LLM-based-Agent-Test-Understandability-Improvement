package org.apache.commons.codec.binary;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringUtils_ESTest_test06 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        CharBuffer charBuffer0 = CharBuffer.wrap((CharSequence) "[\u0000N\u0000Q\u0000.\u00003\u00008\u0000g\u0000~\u00009\u0000u\u0000=\u0000Y\u0000G\u0000O\u0000x\u0000n\u0000W\u0000");
        boolean boolean0 = StringUtils.equals((CharSequence) "[\u0000N\u0000Q\u0000.\u00003\u00008\u0000g\u0000~\u00009\u0000u\u0000=\u0000Y\u0000G\u0000O\u0000x\u0000n\u0000W\u0000", (CharSequence) charBuffer0);
        assertTrue(boolean0);
    }
}
