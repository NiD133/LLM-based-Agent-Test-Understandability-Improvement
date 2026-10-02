package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Iterator;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Element;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test12 extends StreamParser_ESTest_scaffolding {

    /**
     * A freshly constructed StreamParser should always hand back a non-null
     * iterator, even before any input has been supplied for parsing.
     */
    @Test(timeout = 4000)
    public void iteratorIsNeverNull() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        Iterator<Element> elementIterator = streamParser.iterator();

        assertNotNull("iterator() must return a non-null iterator", elementIterator);
    }
}
