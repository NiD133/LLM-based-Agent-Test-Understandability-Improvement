package org.jsoup.parser;

import org.junit.Test;
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
public class StreamParser_ESTest_test02 extends StreamParser_ESTest_scaffolding {

    /**
     * ElementIterator.tail() only reacts to nodes that are Elements. When it is
     * handed a null node it should simply do nothing, without throwing.
     */
    @Test(timeout = 4000)
    public void tailWithNullNodeIsANoOp() throws Throwable {
        StreamParser streamParser = new StreamParser(Parser.xmlParser());
        StreamParser.ElementIterator elementIterator = streamParser.new ElementIterator();

        // A null node is neither an Element nor triggers any queue changes.
        elementIterator.tail((Node) null, -8);
    }
}
