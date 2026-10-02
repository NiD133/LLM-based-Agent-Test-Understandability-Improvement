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
public class StreamParser_ESTest_test09 extends StreamParser_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test09() throws Throwable {
        Parser parser0 = Parser.htmlParser();
        StreamParser streamParser0 = new StreamParser(parser0);
        // Undeclared exception!
        try {
            streamParser0.expectNext("http://www.w3.org/XML/1998/namespace");
            fail("Expecting exception: IllegalStateException");
        } catch (IllegalStateException e) {
            //
            // Could not parse query 'http://www.w3.org/XML/1998/namespace': unexpected token at '//www.w3.org/XML/1998/namespace'
            //
            verifyException("org.jsoup.select.QueryParser", e);
        }
    }
}
