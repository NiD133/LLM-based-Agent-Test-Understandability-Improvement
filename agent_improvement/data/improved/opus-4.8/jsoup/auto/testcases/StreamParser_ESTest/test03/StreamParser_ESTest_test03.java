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
     * Calling remove() on a freshly created ElementIterator must fail, because no
     * element has been returned by next() yet (the iterator's "current" element is
     * still null). The iterator signals this illegal state with a NoSuchElementException.
     */
    @Test(timeout = 4000)
    public void removeWithoutPriorNextThrowsNoSuchElement() throws Throwable {
        StreamParser streamParser = new StreamParser(Parser.xmlParser());
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        try {
            elementIterator.remove();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            // remove() throws before any element has been iterated; no message is set.
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }
}
