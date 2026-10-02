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
public class UnicodeEscaper_ESTest_test1 extends UnicodeEscaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        StringWriter stringWriter0 = new StringWriter(571);
        UnicodeEscaper unicodeEscaper0 = UnicodeEscaper.above(571);
        boolean boolean0 = unicodeEscaper0.translate(629, (Writer) stringWriter0);
        assertEquals("\\u0275", stringWriter0.toString());
        assertTrue(boolean0);
    }
}
