package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test20 extends TokenQueue_ESTest_scaffolding {

    /**
     * After consuming the leading CSS identifier "8", the queue holds "#cX#pxA*".
     * Calling chompBalanced('#', 'p') expects '#' as the open marker and 'p' as the
     * close marker. The input has two '#' openers but only one 'p' closer, so the
     * markers are unbalanced and an IllegalArgumentException must be thrown.
     */
    @Test(timeout = 4000)
    public void test20() throws Throwable {
        // "8" is a valid CSS identifier; after consuming it the queue starts at "#cX#pxA*"
        TokenQueue tokenQueue = new TokenQueue("8#cX#pxA*");
        tokenQueue.consumeCssIdentifier();

        // "#cX#pxA*" has two '#' openers and only one 'p' closer — unbalanced
        try {
            tokenQueue.chompBalanced('#', 'p');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            //
            // Did not find balanced marker at 'cX#pxA*'
            //
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
