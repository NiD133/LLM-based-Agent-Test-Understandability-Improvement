package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.assertSame;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Element;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test08 extends StreamParser_ESTest_scaffolding {

    /**
     * parseFragment(String, Element, String) configures the parser with the supplied input and
     * returns the same StreamParser instance so that calls can be chained.
     */
    @Test(timeout = 4000)
    public void parseFragmentReturnsSameStreamParserInstance() throws Throwable {
        String fragmentInput = "http://www.w3.org/XML/1998/namespace";
        Element noContextElement = null;
        String baseUri = "http://www.w3.org/2000/svg";

        StreamParser streamParser = new StreamParser(Parser.xmlParser());

        StreamParser returnedParser = streamParser.parseFragment(fragmentInput, noContextElement, baseUri);

        assertSame(returnedParser, streamParser);
    }
}
