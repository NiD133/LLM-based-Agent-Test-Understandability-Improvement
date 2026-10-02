package org.jsoup.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Node;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test02 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that ElementIterator.tail() tolerates a null node: because the
     * node is not an Element, the visitor returns without touching any state and
     * without throwing, regardless of the reported depth.
     */
    @Test(timeout = 4000)
    public void tail_withNullNode_isNoOp() throws Throwable {
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        Node nullNode = null;
        int anyDepth = -8;

        elementIterator.tail(nullNode, anyDepth);
    }
}
