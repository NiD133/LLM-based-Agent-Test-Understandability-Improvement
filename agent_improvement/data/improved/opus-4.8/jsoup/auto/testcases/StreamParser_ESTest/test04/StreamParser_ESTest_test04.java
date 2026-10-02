package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test04 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that, after manually registering the parsed Document with the
     * ElementIterator via {@code tail(...)}, the iterator's first {@code next()}
     * yields that Document element, which is a block-level element.
     */
    @Test(timeout = 4000)
    public void next_afterTailingDocument_returnsBlockElement() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        // Parse empty input to obtain a Document node.
        Document document = htmlParser.parseInput("", "");

        // Register the Document as the iterator's tail element so it is the next emitted element.
        int depth = 644;
        elementIterator.tail(document, depth);

        Element firstElement = elementIterator.next();

        assertTrue(firstElement.isBlock());
    }
}
