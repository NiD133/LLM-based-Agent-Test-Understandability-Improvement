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
public class StreamParser_ESTest_test01 extends StreamParser_ESTest_scaffolding {

    /**
     * Verifies that the completed Document keeps the base URI supplied to
     * {@link StreamParser#parse(String, String)} as its location, even when the
     * parsed input is empty.
     */
    @Test(timeout = 4000)
    public void completeReturnsDocumentWithGivenBaseUriAsLocation() throws Throwable {
        // Build a StreamParser backed by a standard HTML parser.
        HtmlTreeBuilder htmlTreeBuilder = new HtmlTreeBuilder();
        Parser parser = new Parser(htmlTreeBuilder);
        StreamParser streamParser = new StreamParser(parser);

        // Parse empty input, associating it with the given base URI.
        String baseUri = "[vo4l)SZUv";
        StreamParser parsedStreamParser = streamParser.parse("", baseUri);

        // Finish the parse and confirm the Document reports the base URI as its location.
        Document document = parsedStreamParser.complete();
        assertEquals(baseUri, document.location());
    }
}
