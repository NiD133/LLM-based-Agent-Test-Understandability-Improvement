package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Document;
import org.jsoup.select.Evaluator;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test06 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that selectFirst() returns the parsed Document (which itself matches an
     * empty-text "matches whole text" evaluator) and that the Document keeps the base URI
     * passed to parse() as its location.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        String input = "http://www.w3.org/1999/xhtml";
        String baseUri = "*oF(";

        StreamParser streamParser = new StreamParser(Parser.htmlParser());
        streamParser.parse(input, baseUri);

        // An empty pattern matches the whole text of every node, so the first match is the Document.
        Evaluator matchesEmptyWholeText = new Evaluator.MatchesWholeText(Pattern.compile(""));
        Document document = (Document) streamParser.selectFirst(matchesEmptyWholeText);

        assertEquals(baseUri, document.location());
    }
}
