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

    private static final String SELECTOR = "org.jsoup.parser.StreamParser";
    private static final String VALIDATE_EXCEPTION_SOURCE = "org.jsoup.helper.Validate";

    @Test(timeout = 4000)
    public void test10() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);

        try {
            streamParser.expectFirst(SELECTOR);
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException exception) {
            verifyException(VALIDATE_EXCEPTION_SOURCE, exception);
        }
    }
}
