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

    private static final int HIGH_SURROGATE_START = 55296;
    private static final int LOWER_ESCAPING_BOUND = 55296;
    private static final int UPPER_ESCAPING_BOUND = 1166;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        UnicodeEscaper escaperWithReversedBounds = UnicodeEscaper.between(LOWER_ESCAPING_BOUND, UPPER_ESCAPING_BOUND);
        StringWriter output = new StringWriter();

        boolean translated = escaperWithReversedBounds.translate(HIGH_SURROGATE_START, (Writer) output);

        assertFalse(translated);
    }
}
