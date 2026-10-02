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

    @Test(timeout = 4000)
    public void test01() throws Throwable {
        HtmlTreeBuilder treeBuilder = new HtmlTreeBuilder();
        Parser parser = new Parser(treeBuilder);
        StreamParser streamParser = new StreamParser(parser);

        StreamParser parserAfterParse = streamParser.parse("", "[vo4l)SZUv");
        Document completedDocument = parserAfterParse.complete();

        assertEquals("[vo4l)SZUv", completedDocument.location());
    }
}
