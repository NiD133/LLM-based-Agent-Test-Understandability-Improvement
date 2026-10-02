package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test09 extends StreamParser_ESTest_scaffolding {

    private static final String INVALID_SELECTOR = "http://www.w3.org/XML/1998/namespace";
    private static final String QUERY_PARSER_CLASS = "org.jsoup.select.QueryParser";

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        try {
            streamParser.expectNext(INVALID_SELECTOR);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException exception) {
            verifyException(QUERY_PARSER_CLASS, exception);
        }
    }
}
