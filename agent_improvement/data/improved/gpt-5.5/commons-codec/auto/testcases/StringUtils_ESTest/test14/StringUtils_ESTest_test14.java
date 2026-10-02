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
public class StringUtils_ESTest_test14 extends StringUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test14() throws Throwable {
        String sourceText = "&Fz(oNy*n^S*wa-O?h";

        ByteBuffer utf8Buffer = StringUtils.getByteBufferUtf8(sourceText);

        assertFalse(utf8Buffer.isDirect());
    }
}
