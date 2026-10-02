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

    private static final String INPUT_HTML = "http://www.w3.org/1999/xhtml";
    private static final String BASE_URI = "*oF(";
    private static final String EMPTY_REGEX = "";

    @Test(timeout = 4000)
    public void test06() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);

        streamParser.parse(INPUT_HTML, BASE_URI);

        Pattern emptyTextPattern = Pattern.compile(EMPTY_REGEX);
        Evaluator.MatchesWholeText matchesWholeText =
                new Evaluator.MatchesWholeText(emptyTextPattern);
        Document matchedDocument =
                (Document) streamParser.selectFirst((Evaluator) matchesWholeText);

        assertEquals(BASE_URI, matchedDocument.location());
    }
}
