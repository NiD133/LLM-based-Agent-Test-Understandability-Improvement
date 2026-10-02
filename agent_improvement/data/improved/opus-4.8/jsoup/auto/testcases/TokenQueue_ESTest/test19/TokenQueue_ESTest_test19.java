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
     * chompBalanced consumes the opening marker, then scans for a matching closing marker.
     * Here the open and close marker are the same character 'O'. The queue "Oy0ADe'8'-Re"
     * starts with 'O' (opening the balance), but there is no second 'O' to close it, so the
     * scanner runs off the end of the queue while still unbalanced and throws
     * IllegalArgumentException via Validate.fail.
     */
    @Test(timeout = 4000)
    public void chompBalanced_withoutMatchingCloseMarker_throwsIllegalArgumentException() throws Throwable {
        TokenQueue tokenQueue = new TokenQueue("Oy0ADe'8'-Re");

        try {
            tokenQueue.chompBalanced('O', 'O');
            fail("Expected IllegalArgumentException because no matching close marker exists");
        } catch (IllegalArgumentException e) {
            // Message: "Did not find balanced marker at 'y0ADe'8'-Re'"
            verifyException("org.jsoup.helper.Validate", e);
        }
    }
}
