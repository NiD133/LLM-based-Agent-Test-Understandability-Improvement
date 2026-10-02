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
public class UnicodeEscaper_ESTest_test5 extends UnicodeEscaper_ESTest_scaffolding {

    /**
     * {@link UnicodeEscaper#below(int)} escapes only code points below the given
     * boundary. With a boundary of 0 there are no code points below it, so every
     * character of the input is passed through verbatim and nothing is escaped.
     */
    @Test(timeout = 4000)
    public void belowZeroEscapesNothingAndCopiesInputUnchanged() throws Throwable {
        UnicodeEscaper escapeBelowZero = UnicodeEscaper.below(0);
        StringWriter output = new StringWriter();

        escapeBelowZero.translate((CharSequence) "5F8", (Writer) output);

        assertEquals("5F8", output.toString());
    }
}
