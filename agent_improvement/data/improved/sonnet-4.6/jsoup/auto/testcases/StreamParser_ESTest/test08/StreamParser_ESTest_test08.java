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
public class StreamParser_ESTest_test08 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test_parseFragment_returnsThisInstance_forFluentChaining() throws Throwable {
        Parser xmlParser = Parser.xmlParser();
        StreamParser streamParser = new StreamParser(xmlParser);

        // parseFragment with a null context element should return the same StreamParser instance (fluent API)
        StreamParser result = streamParser.parseFragment(
            "http://www.w3.org/XML/1998/namespace",
            (Element) null,
            "http://www.w3.org/2000/svg"
        );

        assertSame(result, streamParser);
    }
}
