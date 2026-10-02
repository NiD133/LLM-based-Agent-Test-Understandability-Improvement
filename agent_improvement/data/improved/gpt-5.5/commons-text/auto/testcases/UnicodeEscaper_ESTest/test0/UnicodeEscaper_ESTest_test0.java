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

    private static final int MAXIMUM_CODE_POINT = Integer.MAX_VALUE;
    private static final int INITIAL_WRITER_CAPACITY = 571;
    private static final String ESCAPED_MAXIMUM_CODE_POINT = "\\u7FFFFFFF";

    @Test(timeout = 4000)
    public void test0() throws Throwable {
        UnicodeEscaper escaper = new UnicodeEscaper();
        StringWriter escapedOutput = new StringWriter(INITIAL_WRITER_CAPACITY);

        boolean wasTranslated = escaper.translate(MAXIMUM_CODE_POINT, (Writer) escapedOutput);

        assertEquals(ESCAPED_MAXIMUM_CODE_POINT, escapedOutput.toString());
        assertTrue(wasTranslated);
    }
}
