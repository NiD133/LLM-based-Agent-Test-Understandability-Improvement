package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Node;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test00 extends StreamParser_ESTest_scaffolding {

    /**
     * Streaming a small input and completing it as a fragment should yield a single node.
     *
     * <p>Here the input is the literal string "http://www.w3.org/2000/svg" (not a URL to fetch),
     * and the second argument is the base URI used for resolving links. Because the text contains
     * no markup, the resulting fragment contains exactly one node.</p>
     */
    @Test(timeout = 4000)
    public void completeFragmentOfPlainTextReturnsSingleNode() throws Throwable {
        String input = "http://www.w3.org/2000/svg";
        String baseUri = "http://www.w3.org/1998/math/mathml";

        StreamParser streamParser = new StreamParser(Parser.htmlParser());
        streamParser.parse(input, baseUri);

        List<Node> fragmentNodes = streamParser.completeFragment();

        assertEquals(1, fragmentNodes.size());
    }
}
