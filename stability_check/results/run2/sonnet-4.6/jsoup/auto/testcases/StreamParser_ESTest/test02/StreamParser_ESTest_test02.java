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
     * Verifies that ElementIterator.tail() silently ignores a null node.
     *
     * When tail() receives a null node, the "node instanceof Element" guard is false,
     * so no element is enqueued and no exception is thrown. The negative depth value
     * (-8) is also accepted without error because depth is not validated by tail().
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);

        // Access the inner ElementIterator, which implements NodeVisitor
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        // Calling tail() with a null node and a negative depth should be a no-op:
        // null is not an instanceof Element, so the method body is skipped entirely.
        elementIterator.tail((Node) null, (-8));
    }
}
