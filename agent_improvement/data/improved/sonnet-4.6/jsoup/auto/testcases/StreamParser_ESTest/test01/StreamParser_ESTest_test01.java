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
     * Verifies that after parsing an empty HTML string, the completed Document's
     * location matches the base URI that was supplied to the parse call.
     */
    @Test(timeout = 4000)
    public void test01_parseEmptyInput_documentLocationMatchesBaseUri() throws Throwable {
        String emptyHtml = "";
        String baseUri = "[vo4l)SZUv";

        HtmlTreeBuilder treeBuilder = new HtmlTreeBuilder();
        Parser parser = new Parser(treeBuilder);
        StreamParser streamParser = new StreamParser(parser);

        StreamParser initializedParser = streamParser.parse(emptyHtml, baseUri);
        Document completedDocument = initializedParser.complete();

        assertEquals(baseUri, completedDocument.location());
    }
}
