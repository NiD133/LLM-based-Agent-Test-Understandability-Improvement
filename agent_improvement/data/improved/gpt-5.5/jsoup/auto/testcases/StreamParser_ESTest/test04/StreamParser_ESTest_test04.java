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

    private static final String EMPTY_HTML = "";
    private static final String EMPTY_BASE_URI = "";
    private static final int DOCUMENT_CLOSE_DEPTH = 644;

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();
        Document emptyDocument = htmlParser.parseInput(EMPTY_HTML, EMPTY_BASE_URI);

        elementIterator.tail(emptyDocument, DOCUMENT_CLOSE_DEPTH);

        Element emittedElement = elementIterator.next();
        assertTrue(emittedElement.isBlock());
    }
}
