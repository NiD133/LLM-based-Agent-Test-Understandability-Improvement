package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Evaluator;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test10 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that calling expectFirst() on a StreamParser before calling parse()
     * throws an IllegalArgumentException from Validate, indicating that the parser
     * must be initialized via parse() before any query methods can be used.
     */
    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        try {
            streamParser.expectFirst("org.jsoup.parser.StreamParser");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
