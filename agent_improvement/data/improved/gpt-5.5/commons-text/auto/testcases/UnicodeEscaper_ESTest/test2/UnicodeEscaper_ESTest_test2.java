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
public class UnicodeEscaper_ESTest_test2 extends UnicodeEscaper_ESTest_scaffolding {

    private static final int ONLY_ESCAPED_CODE_POINT = -2014;
    private static final int CODE_POINT_OUTSIDE_ESCAPED_RANGE = -2931;

    @Test(timeout = 4000)
    public void test2() throws Throwable {
        UnicodeEscaper escaper = UnicodeEscaper.between(ONLY_ESCAPED_CODE_POINT, ONLY_ESCAPED_CODE_POINT);
        StringWriter output = new StringWriter();

        boolean translated = escaper.translate(CODE_POINT_OUTSIDE_ESCAPED_RANGE, (Writer) output);

        assertFalse(translated);
    }
}
