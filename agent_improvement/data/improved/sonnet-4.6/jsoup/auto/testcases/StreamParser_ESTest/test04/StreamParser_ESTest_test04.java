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
public class StreamParser_ESTest_test04 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that calling the NodeVisitor tail() callback on a parsed Document
     * enqueues its last element child into the ElementIterator's emit queue, so that
     * the next element returned is a block-level element (the <html> element).
     *
     * Scenario:
     *   1. Build a StreamParser and obtain its inner ElementIterator.
     *   2. Parse an empty HTML string — this creates a Document with default HTML structure
     *      (<html>, <head>, <body>), where the last element child of the Document is <html>.
     *   3. Manually invoke tail() on the Document to simulate the NodeVisitor callback,
     *      which enqueues <html> (the document's lastElementChild) for emission.
     *   4. Call next() on the iterator and assert the returned element is a block element.
     */
    @Test(timeout = 4000)
    public void test04() throws Throwable {
        // Create a standard HTML parser and wrap it in a StreamParser
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        // Obtain the ElementIterator, which implements both Iterator<Element> and NodeVisitor
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        // Parse an empty input to produce a Document with the default <html>/<head>/<body> skeleton
        Document emptyHtmlDocument = htmlParser.parseInput("", "");

        // Simulate the NodeVisitor tail callback on the document node.
        // ElementIterator.tail() sets its internal 'tail' field to the node and adds
        // the node's lastElementChild (<html>) to the emit queue.
        int depth = 644;
        elementIterator.tail(emptyHtmlDocument, depth);

        // next() drains the emit queue and returns <html>, the last element child of the document
        Element htmlElement = elementIterator.next();

        // <html> is a block-level element
        assertTrue(htmlElement.isBlock());
    }
}
