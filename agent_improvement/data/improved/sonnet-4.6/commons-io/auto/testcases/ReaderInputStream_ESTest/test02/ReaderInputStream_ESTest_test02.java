package org.apache.commons.io.input;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.io.StringReader;
import java.nio.charset.CharsetEncoder;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class ReaderInputStream_ESTest_test02 extends ReaderInputStream_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Passing null as the CharsetEncoder causes the default charset to be used.
        // The first character '{' has byte value 123 in the default (UTF-8/ASCII) encoding.
        StringReader reader = new StringReader("{/");
        ReaderInputStream inputStream = new ReaderInputStream(reader, (CharsetEncoder) null);
        int firstByte = inputStream.read();
        assertEquals('{', firstByte);
    }
}
