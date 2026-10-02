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
public class StringUtils_ESTest_test17 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test17() throws Throwable {
        byte[] byteArray0 = StringUtils.getBytesIso8859_1("lI\"Y50tcK(~T,");
        assertEquals(13, byteArray0.length);
    }
}
