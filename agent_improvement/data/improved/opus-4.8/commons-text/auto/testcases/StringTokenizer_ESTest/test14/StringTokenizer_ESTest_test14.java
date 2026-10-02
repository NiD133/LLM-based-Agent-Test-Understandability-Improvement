package org.apache.commons.text;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test14 extends StringTokenizer_ESTest_scaffolding {

    /**
     * A TSV tokenizer obtained without any input text has no content to parse,
     * so {@link StringTokenizer#getContent()} returns {@code null}.
     */
    @Test(timeout = 4000)
    public void getContentReturnsNullWhenNoInputWasSet() throws Throwable {
        StringTokenizer tsvTokenizerWithoutInput = StringTokenizer.getTSVInstance();

        String content = tsvTokenizerWithoutInput.getContent();

        assertNull(content);
    }
}
