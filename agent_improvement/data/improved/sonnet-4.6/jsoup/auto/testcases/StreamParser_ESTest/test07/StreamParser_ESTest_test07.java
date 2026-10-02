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
     * Verifies that selectFirst returns null when no element in the parsed XML
     * contains the target data string.
     *
     * The input is an SVG namespace URL parsed as XML; searching for elements
     * whose text data contains the XML namespace URL yields no match.
     */
    @Test(timeout = 4000)
    public void test07() throws Throwable {
        // Set up an XML stream parser and parse an SVG namespace URI as the document input
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);
        streamParser.parse("http://www.w3.org/2000/svg", "");

        // Build an evaluator that matches elements containing a specific data string
        Evaluator.ContainsData xmlNamespaceEvaluator =
            new Evaluator.ContainsData("http://www.w3.org/XML/1998/namespace");

        // No element in the parsed SVG URI document contains the XML namespace URI as data
        Element result = streamParser.selectFirst((Evaluator) xmlNamespaceEvaluator);
        assertNull(result);
    }
}
