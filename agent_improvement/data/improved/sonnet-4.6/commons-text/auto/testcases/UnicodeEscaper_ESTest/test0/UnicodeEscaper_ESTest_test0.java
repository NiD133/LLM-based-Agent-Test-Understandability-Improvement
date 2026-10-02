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

    /**
     * Verifies that translating Integer.MAX_VALUE (0x7FFFFFFF) as a Unicode code point
     * produces a supplementary-plane escape sequence "翿FFFF" and returns true,
     * since the default UnicodeEscaper covers all code points [0, Integer.MAX_VALUE].
     */
    @Test(timeout = 4000)
    public void test_translateMaxCodePoint_writesSupplementaryUnicodeEscape() throws Throwable {
        // The default UnicodeEscaper escapes every code point in [0, Integer.MAX_VALUE]
        UnicodeEscaper unicodeEscaper0 = new UnicodeEscaper();
        StringWriter stringWriter0 = new StringWriter(571);

        // Integer.MAX_VALUE (0x7FFFFFFF) exceeds the BMP range (> 0xFFFF),
        // so translate() delegates to toUtf16Escape(), producing "\\u7FFFFFFF"
        boolean boolean0 = unicodeEscaper0.translate(Integer.MAX_VALUE, (Writer) stringWriter0);

        assertEquals("\\u7FFFFFFF", stringWriter0.toString());
        assertTrue(boolean0);
    }
}
