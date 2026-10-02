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
     * The no-arg constructor escapes every code point. Translating the largest
     * possible code point (Integer.MAX_VALUE) should write its 8-digit Unicode
     * escape and report that it handled the code point.
     */
    @Test(timeout = 4000)
    public void translateMaxCodePointWritesEightDigitEscape() throws Throwable {
        UnicodeEscaper escaperForAllCodePoints = new UnicodeEscaper();
        StringWriter output = new StringWriter();

        boolean wasTranslated = escaperForAllCodePoints.translate(Integer.MAX_VALUE, (Writer) output);

        assertTrue("translate should report the code point was escaped", wasTranslated);
        assertEquals("\\u7FFFFFFF", output.toString());
    }
}
