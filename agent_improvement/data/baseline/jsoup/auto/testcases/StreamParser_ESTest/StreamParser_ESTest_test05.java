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
        Parser parser0 = Parser.htmlParser();
        StreamParser streamParser0 = new StreamParser(parser0);
        StreamParser streamParser1 = streamParser0.parse("http://www.w3.org/XML/1998/namespace", "http://www.w3.org/1998/Math/MathML");
        StreamParser.ElementIterator streamParser_ElementIterator0 = streamParser1.new ElementIterator();
        // Undeclared exception!
        try {
            streamParser_ElementIterator0.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            //
            // no message in exception (getMessage() returned null)
            //
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }
}
