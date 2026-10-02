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
public class StreamParser_ESTest_test07 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Parser parser0 = Parser.xmlParser();
        StreamParser streamParser0 = new StreamParser(parser0);
        streamParser0.parse("http://www.w3.org/2000/svg", "");
        Evaluator.ContainsData evaluator_ContainsData0 = new Evaluator.ContainsData("http://www.w3.org/XML/1998/namespace");
        Element element0 = streamParser0.selectFirst((Evaluator) evaluator_ContainsData0);
        assertNull(element0);
    }
}
