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

    private static final int LOW_BOUND_GREATER_THAN_HIGH_BOUND = 55296;
    private static final int HIGH_BOUND_LOWER_THAN_LOW_BOUND = 1166;
    private static final int CODE_POINT_AT_LOW_BOUND = 55296;

    @Test(timeout = 4000)
    public void test4() throws Throwable {
        UnicodeEscaper escaperWithReversedRange =
                UnicodeEscaper.between(LOW_BOUND_GREATER_THAN_HIGH_BOUND, HIGH_BOUND_LOWER_THAN_LOW_BOUND);
        StringWriter translatedOutput = new StringWriter();

        boolean wasTranslated = escaperWithReversedRange.translate(CODE_POINT_AT_LOW_BOUND, (Writer) translatedOutput);

        assertFalse(wasTranslated);
    }
}
