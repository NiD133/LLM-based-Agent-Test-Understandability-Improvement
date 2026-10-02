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
public class StreamParser_ESTest_test00 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        // Use an HTML parser to parse an SVG namespace URI string as content, with a MathML URI as the base URI
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        // The SVG namespace URI is used as the HTML content string; the MathML URI is the base URI
        String htmlContent = "http://www.w3.org/2000/svg";
        String baseUri = "http://www.w3.org/1998/math/mathml";
        StreamParser initializedParser = streamParser.parse(htmlContent, baseUri);

        // Complete the fragment parse and verify exactly one node is produced
        List<Node> fragmentNodes = initializedParser.completeFragment();
        assertEquals(1, fragmentNodes.size());
    }
}
