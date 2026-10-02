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
public class StreamParser_ESTest_test02 extends StreamParser_ESTest_scaffolding {

    /**
     * ElementIterator.tail(node, depth) only reacts when the visited node is an
     * Element. Passing a null node should therefore be a no-op and must not throw,
     * even with a negative depth.
     */
    @Test(timeout = 4000)
    public void tail_withNullNode_isNoOp() throws Throwable {
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        Node nullNode = null;
        int depth = -8;

        // Should complete without throwing, since a null node is not an Element.
        elementIterator.tail(nullNode, depth);
    }
}
