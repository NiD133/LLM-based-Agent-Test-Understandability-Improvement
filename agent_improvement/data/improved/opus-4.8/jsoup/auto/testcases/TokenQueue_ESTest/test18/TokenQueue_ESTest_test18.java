package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.fail;
import static org.evosuite.runtime.EvoAssertions.verifyException;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test18 extends TokenQueue_ESTest_scaffolding {

    /**
     * chompBalanced should reject input whose single quote character acts as both the
     * opening and closing marker. With queue content "'" and open == close == '\'',
     * the quote is consumed as the opener but no matching closer remains, so the queue
     * is unbalanced and Validate.fail raises an IllegalArgumentException.
     */
    @Test(timeout = 4000)
    public void chompBalanced_withUnbalancedQuoteMarker_throwsIllegalArgumentException() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("'");

        try {
            tokenQueue.chompBalanced('\'', '\'');
            fail("Expecting exception: IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            // Validate.fail reports: "Did not find balanced marker at ''"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
