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
public class StreamParser_ESTest_test03 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that calling remove() on a freshly created ElementIterator — before any
     * call to next() — throws NoSuchElementException, because there is no "current"
     * element to remove.
     */
    @Test(timeout = 4000)
    public void test03() throws Throwable {
        // Build a StreamParser backed by an XML parser
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);

        // Obtain an ElementIterator without advancing it (no next() call)
        StreamParser.ElementIterator iterator = streamParser.new ElementIterator();

        // remove() must reject the call because no element has been consumed yet
        try {
            iterator.remove();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }
}
