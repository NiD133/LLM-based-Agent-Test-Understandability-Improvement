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

    /**
     * A freshly constructed ElementIterator over an input that yields no emittable
     * elements must throw NoSuchElementException on next(), since there is nothing
     * to return.
     */
    @Test(timeout = 4000)
    public void next_onIteratorWithNoElements_throwsNoSuchElementException() throws Throwable {
        // Set up a StreamParser and hand it a short input that produces no
        // elements ready to be emitted by the iterator.
        StreamParser streamParser = new StreamParser(Parser.htmlParser());
        String input = "http://www.w3.org/XML/1998/namespace";
        String baseUri = "http://www.w3.org/1998/Math/MathML";
        StreamParser parsedStreamParser = streamParser.parse(input, baseUri);

        // Create a brand-new iterator with an empty emit queue.
        StreamParser.ElementIterator elementIterator = parsedStreamParser.new ElementIterator();

        // With no elements available, next() must fail with NoSuchElementException.
        try {
            elementIterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // Thrown by StreamParser.ElementIterator.next() with no message.
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }
}
