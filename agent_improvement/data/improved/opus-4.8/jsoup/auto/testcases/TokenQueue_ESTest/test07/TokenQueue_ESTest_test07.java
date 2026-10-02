package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test07 extends TokenQueue_ESTest_scaffolding {

    /**
     * escapeCssIdentifier should leave letters and digits untouched, escape the
     * control character U+007F (DEL) as its hex code point ("\7f "), and
     * backslash-escape the punctuation characters '$' and '&'.
     */
    @Test(timeout = 4000)
    public void escapesControlAndPunctuationCharacters() throws Throwable {
        //  is the DEL control character, sitting between "p8moD0M" and "0$t5mTH&".
        String rawIdentifier = "p8moD0M0$t5mTH&";

        String escaped = TokenQueue.escapeCssIdentifier(rawIdentifier);

        assertEquals("p8moD0M\\7f 0\\$t5mTH\\&", escaped);
    }
}
