package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test19 extends TokenQueue_ESTest_scaffolding {

    /**
     * When the open and close markers are the same character, chompBalanced cannot
     * distinguish a closing token from an opening one. Here the queue starts with
     * 'O', which is consumed as the opener (depth = 1), but no second 'O' follows,
     * so the queue is exhausted with unmatched depth and an IllegalArgumentException
     * is thrown reporting the unmatched content.
     */
    @Test(timeout = 4000)
    public void test19_chompBalanced_sameOpenAndCloseChar_throwsWhenNoMatchingClose() throws Throwable {
        // 'O' is the first character, so it acts as the opening marker;
        // no second 'O' exists in the rest of the string to close the balance.
        TokenQueue tokenQueue = new TokenQueue("Oy0ADe'8'-Re");

        try {
            tokenQueue.chompBalanced('O', 'O');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // The exception message identifies the unmatched content that followed the opener.
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
