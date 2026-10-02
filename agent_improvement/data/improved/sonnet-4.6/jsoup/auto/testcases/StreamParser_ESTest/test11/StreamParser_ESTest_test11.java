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
public class StreamParser_ESTest_test11 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that selectNext(Evaluator) does not throw when searching for
     * :last-child elements in a partially-parsed document. The HTML input "time"
     * is parsed with an SVG base URI; since no element can be confirmed as a
     * last-child during streaming, selectNext returns null without error.
     */
    @Test(timeout = 4000)
    public void test11() throws Throwable {
        // Build a StreamParser backed by the standard HTML parser
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        // Initialise a parse of the string "time" (a single HTML element name)
        // using an SVG namespace base URI
        String svgBaseUri = "http://www.w3.org/2000/svg";
        StreamParser initializedParser = streamParser.parse("time", svgBaseUri);

        // Attempt to find the next element matching :last-child.
        // During streaming the parser cannot know whether a sibling will follow,
        // so this evaluator cannot match; the call should complete without throwing.
        Evaluator.IsLastChild isLastChildEvaluator = new Evaluator.IsLastChild();
        initializedParser.selectNext((Evaluator) isLastChildEvaluator);
    }
}
