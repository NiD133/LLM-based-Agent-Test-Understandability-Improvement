package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test02 extends WordUtils_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void test02() throws Throwable {
        // wrapLength=0 is clamped to 1, so each character triggers a wrap;
        // the wrapOn regex "\b.*" (word boundary followed by anything) causes each
        // character to be separated by newLineStr "\b.*", with wrapLongWords=true
        // ensuring every character in the short-word sequence gets its own line segment
        String inputText = "3^s.6";
        int wrapLength = 0;
        String newLineStr = "\b.*";
        boolean wrapLongWords = true;
        String wrapOnPattern = "\b.*";

        String result = WordUtils.wrap(inputText, wrapLength, newLineStr, wrapLongWords, wrapOnPattern);

        assertEquals("3\b.*^\b.*s\b.*.\b.*6", result);
    }
}
