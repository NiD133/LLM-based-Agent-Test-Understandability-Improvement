package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test10 extends StreamParser_ESTest_scaffolding {

    /**
     * Calling expectFirst(...) before any input has been supplied via parse() must fail:
     * there is no Document to query yet, so Validate rejects the call with an
     * IllegalArgumentException ("Must run parse() before calling.").
     */
    @Test(timeout = 4000)
    public void expectFirstWithoutParseThrowsIllegalArgument() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        try {
            streamParser.expectFirst("org.jsoup.parser.StreamParser");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate.notNull(document, "Must run parse() before calling.") rejects the unparsed parser
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
