package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test05 extends TokenQueue_ESTest_scaffolding {

    // A single-quote is not a valid CSS identifier character, so consumeCssIdentifier()
    // should return an empty string without consuming any input.
    @Test(timeout = 4000)
    public void test05() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("'");
        String identifier = tokenQueue.consumeCssIdentifier();
        assertEquals("", identifier);
    }
}
