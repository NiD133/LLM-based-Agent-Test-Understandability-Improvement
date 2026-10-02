package org.apache.commons.text;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test15 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A TSV tokenizer should preserve the exact input string it was created from,
     * retrievable via getContent() without any modification.
     */
    @Test(timeout = 4000)
    public void getContentReturnsOriginalInputString() throws Throwable {
        String inputText = "add() Ss unsup!oted";

        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance(inputText);
        String content = tsvTokenizer.getContent();

        assertEquals(inputText, content);
    }
}
