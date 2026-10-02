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
public class StreamParser_ESTest_test05 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);
        StreamParser parsedStream = streamParser.parse("http://www.w3.org/XML/1998/namespace", "http://www.w3.org/1998/Math/MathML");
        StreamParser.ElementIterator elementIterator = parsedStream.new ElementIterator();

        try {
            elementIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // The iterator has no element to return, so next() fails without an exception message.
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }
}
