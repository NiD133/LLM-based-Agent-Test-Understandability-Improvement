package org.apache.commons.text;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class WordUtils_ESTest_test18 extends WordUtils_ESTest_scaffolding {

    // Verifies that initials() collects the first character of each whitespace-delimited word,
    // including non-letter characters such as the leading hyphen in "-1".
    @Test(timeout = 4000)
    public void test_initials_includesLeadingHyphenOfLastWord() throws Throwable {
        String initials = WordUtils.initials("upper valuecannot be less than -1");
        assertEquals("uvblt-", initials);
    }
}
