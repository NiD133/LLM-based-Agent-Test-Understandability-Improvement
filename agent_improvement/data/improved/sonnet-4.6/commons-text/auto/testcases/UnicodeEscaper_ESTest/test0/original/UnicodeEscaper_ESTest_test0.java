package org.apache.commons.text.translate;

import org.junit.Test;
import static org.junit.Assert.*;
import java.io.StringWriter;
import java.io.Writer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class UnicodeEscaper_ESTest_test0 extends UnicodeEscaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        UnicodeEscaper unicodeEscaper0 = new UnicodeEscaper();
        StringWriter stringWriter0 = new StringWriter(571);
        boolean boolean0 = unicodeEscaper0.translate(Integer.MAX_VALUE, (Writer) stringWriter0);
        assertEquals("\\u7FFFFFFF", stringWriter0.toString());
        assertTrue(boolean0);
    }
}
