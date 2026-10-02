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
     * The NodeVisitor.tail() contract is called for every closing tag during
     * tree traversal. ElementIterator's implementation only acts when the node
     * is an Element; a null argument must therefore be a no-op (null is not
     * an instance of Element) regardless of the depth value supplied.
     */
    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // Arrange: build a StreamParser backed by an XML parser and obtain its
        // inner ElementIterator (which implements NodeVisitor).
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        // Act & Assert: calling tail() with a null node and a negative depth
        // must complete without throwing an exception, because the null check
        // (node instanceof Element) evaluates to false and the method returns
        // immediately.
        elementIterator.tail((Node) null, -8);
    }
}
