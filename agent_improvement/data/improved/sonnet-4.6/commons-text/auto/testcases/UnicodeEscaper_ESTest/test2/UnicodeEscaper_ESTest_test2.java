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

    /**
     * Verifies that translate() returns false when the given code point falls
     * outside the escaper's inclusive range [codePointLow, codePointHigh].
     *
     * UnicodeEscaper.between(-2014, -2014) creates an escaper that only escapes
     * the single code point -2014. When translate() is called with -2931, which
     * is less than -2014, the code point is outside the range so no escaping
     * occurs and the method returns false.
     */
    @Test(timeout = 4000)
    public void test2() throws Throwable {
        // Escaper configured to escape only code points in the range [-2014, -2014]
        UnicodeEscaper escaper = UnicodeEscaper.between((-2014), (-2014));
        StringWriter output = new StringWriter();

        // Code point -2931 is below the escaper's lower bound (-2014), so it is outside the range
        boolean wasEscaped = escaper.translate((-2931), (Writer) output);

        // Expect false: code point outside the configured range should not be escaped
        assertFalse(wasEscaped);
    }
}
