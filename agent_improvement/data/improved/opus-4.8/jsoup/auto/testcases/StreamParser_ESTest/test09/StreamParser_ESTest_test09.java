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

    /**
     * Verifies that {@link StreamParser#expectNext(String)} rejects a malformed CSS
     * selector query. The string "http://www.w3.org/XML/1998/namespace" is not a valid
     * selector, so the underlying QueryParser fails and throws an IllegalStateException
     * while trying to compile the query.
     */
    @Test(timeout = 4000)
    public void expectNextWithInvalidQueryThrowsIllegalStateException() throws Throwable {
        StreamParser streamParser = new StreamParser(Parser.htmlParser());

        String invalidQuery = "http://www.w3.org/XML/1998/namespace";
        try {
            streamParser.expectNext(invalidQuery);
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            // Could not parse query 'http://www.w3.org/XML/1998/namespace':
            // unexpected token at '//www.w3.org/XML/1998/namespace'
            verifyException("org.jsoup.select.QueryParser", e);
        }
    }
}
