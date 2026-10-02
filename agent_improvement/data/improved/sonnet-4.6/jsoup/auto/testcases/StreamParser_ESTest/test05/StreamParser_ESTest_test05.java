package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.NoSuchElementException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test05 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test05() throws Throwable {
        // Initialize a StreamParser with some HTML content and a base URI
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);
        String htmlContent = "http://www.w3.org/XML/1998/namespace";
        String baseUri = "http://www.w3.org/1998/Math/MathML";
        StreamParser initializedParser = streamParser.parse(htmlContent, baseUri);

        // Create a new ElementIterator instance directly rather than via iterator().
        // This iterator is not registered as a node listener with the tree builder,
        // so it will never receive emitted elements.
        StreamParser.ElementIterator unregisteredIterator = initializedParser.new ElementIterator();

        // Calling next() on an iterator with no available elements must throw NoSuchElementException
        try {
            unregisteredIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }
}
