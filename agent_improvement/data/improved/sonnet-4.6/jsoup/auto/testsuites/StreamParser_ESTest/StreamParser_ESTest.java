/*
 * Improved for understandability from EvoSuite-generated test.
 * Original: StreamParser_ESTest.java
 */

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
import org.jsoup.parser.HtmlTreeBuilder;
import org.jsoup.parser.Parser;
import org.jsoup.parser.StreamParser;
import org.jsoup.select.Evaluator;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class) @EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void completeFragment_returnsOneNode() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        StreamParser parsed = streamParser.parse("http://www.w3.org/2000/svg", "http://www.w3.org/1998/math/mathml");
        List<Node> fragmentNodes = parsed.completeFragment();
        assertEquals(1, fragmentNodes.size());
    }

    @Test(timeout = 4000)
    public void complete_returnsDocumentWithExpectedLocation() throws Throwable {
        HtmlTreeBuilder treeBuilder = new HtmlTreeBuilder();
        Parser parser = new Parser(treeBuilder);
        StreamParser streamParser = new StreamParser(parser);
        StreamParser parsed = streamParser.parse("", "[vo4l)SZUv");
        Document document = parsed.complete();
        assertEquals("[vo4l)SZUv", document.location());
    }

    @Test(timeout = 4000)
    public void elementIterator_tailWithNullNode_doesNotThrow() throws Throwable {
        Parser parser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(parser);
        StreamParser.ElementIterator iterator = streamParser.new ElementIterator();
        // null is not instanceof Element, so tail() should be a no-op
        iterator.tail((Node) null, -8);
    }

    @Test(timeout = 4000)
    public void elementIterator_removeBeforeNext_throwsNoSuchElementException() throws Throwable {
        Parser parser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(parser);
        StreamParser.ElementIterator iterator = streamParser.new ElementIterator();
        try {
            iterator.remove();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }

    @Test(timeout = 4000)
    public void elementIterator_afterTailOnDocument_nextReturnsBlockElement() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        StreamParser.ElementIterator iterator = streamParser.new ElementIterator();
        Document document = parser.parseInput("", "");
        iterator.tail(document, 644);
        Element element = iterator.next();
        assertTrue(element.isBlock());
    }

    @Test(timeout = 4000)
    public void elementIterator_nextOnCompletedParse_throwsNoSuchElementException() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        StreamParser parsed = streamParser.parse("http://www.w3.org/XML/1998/namespace", "http://www.w3.org/1998/Math/MathML");
        StreamParser.ElementIterator iterator = parsed.new ElementIterator();
        try {
            iterator.next();
            fail("Expecting exception: NoSuchElementException");
        } catch (NoSuchElementException e) {
            verifyException("org.jsoup.parser.StreamParser$ElementIterator", e);
        }
    }

    @Test(timeout = 4000)
    public void selectFirst_withMatchesWholeTextEvaluator_returnsDocumentWithExpectedLocation() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        streamParser.parse("http://www.w3.org/1999/xhtml", "*oF(");
        Pattern matchAll = Pattern.compile("");
        Evaluator.MatchesWholeText evaluator = new Evaluator.MatchesWholeText(matchAll);
        Document document = (Document) streamParser.selectFirst((Evaluator) evaluator);
        assertEquals("*oF(", document.location());
    }

    @Test(timeout = 4000)
    public void selectFirst_withContainsDataEvaluator_returnsNullWhenNoMatch() throws Throwable {
        Parser parser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(parser);
        streamParser.parse("http://www.w3.org/2000/svg", "");
        Evaluator.ContainsData evaluator = new Evaluator.ContainsData("http://www.w3.org/XML/1998/namespace");
        Element result = streamParser.selectFirst((Evaluator) evaluator);
        assertNull(result);
    }

    @Test(timeout = 4000)
    public void parseFragment_withNullContext_returnsSameInstance() throws Throwable {
        Parser parser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(parser);
        StreamParser result = streamParser.parseFragment("http://www.w3.org/XML/1998/namespace", (Element) null, "http://www.w3.org/2000/svg");
        assertSame(streamParser, result);
    }

    @Test(timeout = 4000)
    public void expectNext_withInvalidCssQuery_throwsIllegalStateException() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        try {
            streamParser.expectNext("http://www.w3.org/XML/1998/namespace");
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            verifyException("org.jsoup.select.QueryParser", e);
        }
    }

    @Test(timeout = 4000)
    public void expectFirst_withoutPriorParse_throwsIllegalArgumentException() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        try {
            streamParser.expectFirst("org.jsoup.parser.StreamParser");
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            verifyException("org.jsoup.helper.Validate", e);
        }
    }

    @Test(timeout = 4000)
    public void selectNext_withIsLastChildEvaluator_doesNotThrow() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        Evaluator.IsLastChild evaluator = new Evaluator.IsLastChild();
        StreamParser parsed = streamParser.parse("time", "http://www.w3.org/2000/svg");
        parsed.selectNext((Evaluator) evaluator);
    }

    @Test(timeout = 4000)
    public void iterator_returnsNonNullIterator() throws Throwable {
        Parser parser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(parser);
        Iterator<Element> iterator = streamParser.iterator();
        assertNotNull(iterator);
    }
}
