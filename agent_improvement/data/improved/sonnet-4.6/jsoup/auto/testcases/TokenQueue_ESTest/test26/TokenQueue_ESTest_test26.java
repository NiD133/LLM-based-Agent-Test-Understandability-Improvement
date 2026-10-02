package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test26 extends TokenQueue_ESTest_scaffolding {

    // matchChomp(char) returns true and consumes the character when the queue starts with that character
    @Test(timeout = 4000)
    public void test26_matchChompReturnsTrueWhenQueueStartsWithMatchingChar() throws Throwable {
        TokenQueue queue = new TokenQueue("722m6%0O=.8DAypB");
        boolean matched = queue.matchChomp('7');
        assertTrue(matched);
    }
}
