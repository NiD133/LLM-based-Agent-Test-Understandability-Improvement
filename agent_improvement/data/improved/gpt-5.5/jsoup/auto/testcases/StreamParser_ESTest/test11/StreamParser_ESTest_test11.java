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

    private static final String INPUT_HTML = "time";
    private static final String SVG_BASE_URI = "http://www.w3.org/2000/svg";

    @Test(timeout = 4000)
    public void test11() throws Throwable {
        Parser htmlParser = Parser.htmlParser();
        StreamParser streamParser = new StreamParser(htmlParser);
        Evaluator.IsLastChild lastChildEvaluator = new Evaluator.IsLastChild();

        StreamParser parsedStream = streamParser.parse(INPUT_HTML, SVG_BASE_URI);

        parsedStream.selectNext((Evaluator) lastChildEvaluator);
    }
}
