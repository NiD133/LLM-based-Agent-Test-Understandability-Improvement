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

    private static final int ESCAPE_THRESHOLD = 571;
    private static final int CODE_POINT_ABOVE_THRESHOLD = 629;
    private static final String ESCAPED_CODE_POINT = "\\u0275";

    @Test(timeout = 4000)
    public void test1() throws Throwable {
        StringWriter output = new StringWriter(ESCAPE_THRESHOLD);
        UnicodeEscaper escaper = UnicodeEscaper.above(ESCAPE_THRESHOLD);

        boolean translated = escaper.translate(CODE_POINT_ABOVE_THRESHOLD, (Writer) output);

        assertEquals(ESCAPED_CODE_POINT, output.toString());
        assertTrue(translated);
    }
}
