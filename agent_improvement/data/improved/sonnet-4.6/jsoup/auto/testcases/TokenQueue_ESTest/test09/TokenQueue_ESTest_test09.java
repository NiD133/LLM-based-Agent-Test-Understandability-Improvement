package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test09 extends TokenQueue_ESTest_scaffolding {

    // Square brackets are not valid CSS identifier characters and must be escaped with a backslash.
    // A leading hyphen is a valid CSS identifier character and is preserved as-is.
    @Test(timeout = 4000)
    public void test_escapeCssIdentifier_escapesSquareBracketsWhilePreservingLeadingHyphen() throws Throwable {
        String inputWithSquareBrackets = "-[-Tg0Y[E";
        String escaped = TokenQueue.escapeCssIdentifier(inputWithSquareBrackets);
        assertEquals("-\\[-Tg0Y\\[E", escaped);
    }
}
