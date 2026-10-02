package org.jsoup.parser;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.select.Evaluator;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StreamParser_ESTest_test11 extends StreamParser_ESTest_scaffolding {

    /**
     * selectNext with an :last-child style evaluator should run the streaming parse
     * over the input without error (the input has no matching element).
     */
    @Test(timeout = 4000)
    public void selectNextWithIsLastChildEvaluatorRunsParse() throws Throwable {
        StreamParser streamParser = new StreamParser(Parser.htmlParser());
        Evaluator isLastChild = new Evaluator.IsLastChild();

        StreamParser parsing = streamParser.parse("time", "http://www.w3.org/2000/svg");
        parsing.selectNext(isLastChild);
    }
}
