package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import java.util.List;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Node;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test00 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test00() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        String namespaceInput = "http://www.w3.org/2000/svg";
        String baseUri = "http://www.w3.org/1998/math/mathml";
        StreamParser parsedStream = streamParser.parse(namespaceInput, baseUri);

        List<Node> fragmentNodes = parsedStream.completeFragment();

        assertEquals(1, fragmentNodes.size());
    }
}
