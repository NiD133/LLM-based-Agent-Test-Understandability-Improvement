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
     * The NodeVisitor.tail() callback is only meaningful when the node is an
     * Element instance. When null is passed, the "instanceof Element" guard
     * evaluates to false and the method returns without performing any action,
     * so no NullPointerException should be thrown.
     */
    @Test(timeout = 4000)
    public void test02_tailWithNullNode_doesNothing() throws Throwable {
        // Arrange: build a StreamParser backed by an XML parser and obtain its ElementIterator
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        // Act & Assert: calling tail() with a null node and an arbitrary depth should not throw
        elementIterator.tail((Node) null, -8);
    }
}
