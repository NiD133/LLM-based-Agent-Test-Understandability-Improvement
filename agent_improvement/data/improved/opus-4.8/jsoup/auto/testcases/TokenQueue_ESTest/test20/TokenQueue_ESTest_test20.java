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
     * chompBalanced throws IllegalArgumentException when the queue runs out before the
     * opening marker is balanced by a matching closing marker.
     *
     * Here the queue starts as "8#cX#pxA*". Consuming the leading CSS identifier ("8")
     * leaves "#cX#pxA*". Chomping balanced between '#' (open) and 'p' (close) then never
     * returns to a balanced depth, so Validate.fail reports the unbalanced remainder.
     */
    @Test(timeout = 4000)
    public void chompBalancedFailsWhenMarkersAreUnbalanced() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("8#cX#pxA*");

        // Consume the leading identifier, leaving "#cX#pxA*" on the queue.
        tokenQueue.consumeCssIdentifier();

        try {
            tokenQueue.chompBalanced('#', 'p');
            fail("Expecting exception: IllegalArgumentException (unbalanced markers)");
        } catch (IllegalArgumentException e) {
            // Validate.fail raises this with message: "Did not find balanced marker at 'cX#pxA*'"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
