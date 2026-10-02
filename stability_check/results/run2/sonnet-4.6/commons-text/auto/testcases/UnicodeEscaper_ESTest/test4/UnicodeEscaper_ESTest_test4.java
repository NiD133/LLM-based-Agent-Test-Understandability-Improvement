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

    /**
     * When UnicodeEscaper.between() is given an inverted range (low > high),
     * the translate() method returns false for a code point that exceeds the upper
     * boundary, because the internal check `codePoint > above` is immediately true
     * and the method short-circuits without writing anything.
     *
     * Concretely: between(55296, 1166) sets below=55296, above=1166.
     * Translating code point 55296 fails the `codePoint > above` test
     * (55296 > 1166), so translate() returns false.
     */
    @Test(timeout = 4000)
    public void test4() throws Throwable {
        // 55296 (0xD800) is used as the "low" boundary; 1166 (0x048E) as the "high" boundary.
        // The range is inverted (low > high), so no code point can satisfy both bounds.
        final int invertedRangeLow  = 55296;
        final int invertedRangeHigh = 1166;

        UnicodeEscaper escaper = UnicodeEscaper.between(invertedRangeLow, invertedRangeHigh);
        Writer output = new StringWriter();

        // Translating 55296 returns false: the code point exceeds `above` (1166),
        // so the escaper considers it out-of-range and writes nothing.
        boolean translated = escaper.translate(invertedRangeLow, output);

        assertFalse("translate() must return false when the code point lies outside the (inverted) range",
                    translated);
    }
}
