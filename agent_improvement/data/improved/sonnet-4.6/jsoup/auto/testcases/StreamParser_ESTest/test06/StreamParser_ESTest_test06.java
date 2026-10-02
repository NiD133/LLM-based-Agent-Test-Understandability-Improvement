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
public class StreamParser_ESTest_test06 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // Set up a StreamParser backed by an HTML parser
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        // Parse an HTML string using "*oF(" as the base URI
        String htmlContent = "http://www.w3.org/1999/xhtml";
        String baseUri = "*oF(";
        streamParser.parse(htmlContent, baseUri);

        // Build an evaluator that matches elements whose full text matches the empty pattern
        Pattern emptyPattern = Pattern.compile("");
        Evaluator.MatchesWholeText matchesWholeTextEvaluator = new Evaluator.MatchesWholeText(emptyPattern);

        // The first match is the Document root element itself (it has no text, matching the empty pattern)
        Document document = (Document) streamParser.selectFirst((Evaluator) matchesWholeTextEvaluator);

        // The document's location should equal the base URI supplied during parse
        assertEquals("*oF(", document.location());
    }
}
