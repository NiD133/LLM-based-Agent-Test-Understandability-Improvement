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
public class UnicodeEscaper_ESTest_test4 extends UnicodeEscaper_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        UnicodeEscaper unicodeEscaper0 = UnicodeEscaper.between(55296, 1166);
        StringWriter stringWriter0 = new StringWriter();
        boolean boolean0 = unicodeEscaper0.translate(55296, (Writer) stringWriter0);
        assertFalse(boolean0);
    }
}
