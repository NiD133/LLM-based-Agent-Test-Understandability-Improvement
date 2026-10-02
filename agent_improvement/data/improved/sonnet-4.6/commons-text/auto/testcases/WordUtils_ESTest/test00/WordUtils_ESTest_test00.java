package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test00 extends WordUtils_ESTest_scaffolding {

    // The same string is used as input text, newline separator, and wrap-on regex pattern.
    private static final String SHARED_TOKEN = "|eT ($ElK)2^p:";

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // wrapLength=-3137 is treated as 1 (minimum); wrapLongWords=false means long words are not broken.
        // The wrapOn regex ("|eT ($ElK)2^p:") matches split points within the input string,
        // and newLineStr ("|eT ($ElK)2^p:") is inserted at each break.
        String wrappedResult = WordUtils.wrap(SHARED_TOKEN, (-3137), SHARED_TOKEN, false, SHARED_TOKEN);
        assertEquals("eT|eT ($ElK)2^p:$E|eT ($ElK)2^p:)2|eT ($ElK)2^p:p:", wrappedResult);
    }
}
