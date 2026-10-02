package org.apache.commons.text;

import org.junit.Test;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class StringTokenizer_ESTest_test14 extends StringTokenizer_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void getContent_onTsvInstanceWithNoInput_returnsNull() throws Throwable {
        StringTokenizer tsvTokenizer = StringTokenizer.getTSVInstance();
        String content = tsvTokenizer.getContent();
        // getContent() returns null when the tokenizer was created without input
    }
}
