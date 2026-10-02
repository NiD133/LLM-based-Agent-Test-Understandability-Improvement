package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Element;
import org.jsoup.select.Evaluator;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test07 extends StreamParser_ESTest_scaffolding {

    /**
     * selectFirst returns null when the streamed (empty) document contains no
     * element matching the evaluator.
     */
    @Test(timeout = 4000)
    public void selectFirstReturnsNullWhenNoElementMatches() throws Throwable {
        // Set up a StreamParser and feed it the input with an empty base URI.
        StreamParser streamParser = new StreamParser(Parser.xmlParser());
        streamParser.parse("http://www.w3.org/2000/svg", "");

        // Query for an element containing some data; the empty document has none.
        Evaluator containsData = new Evaluator.ContainsData("http://www.w3.org/XML/1998/namespace");
        Element match = streamParser.selectFirst(containsData);

        assertNull(match);
    }
}
