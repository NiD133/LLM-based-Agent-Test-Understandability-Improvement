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

    private static final int LOWER_SURROGATE_BOUNDARY = 55296;
    private static final int REVERSED_UPPER_BOUNDARY = 1166;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        UnicodeEscaper escaperWithReversedRange = UnicodeEscaper.between(
                LOWER_SURROGATE_BOUNDARY,
                REVERSED_UPPER_BOUNDARY);
        StringWriter output = new StringWriter();

        boolean wasTranslated = escaperWithReversedRange.translate(
                LOWER_SURROGATE_BOUNDARY,
                (Writer) output);

        assertFalse(wasTranslated);
    }
}
