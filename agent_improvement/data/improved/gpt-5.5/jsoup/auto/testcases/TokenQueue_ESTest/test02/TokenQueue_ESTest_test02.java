package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TokenQueue_ESTest_test02 extends TokenQueue_ESTest_scaffolding {

    @Test(timeout = 4000)
    public void chompBalancedWithoutOpeningMarkerLeavesQuoteAsResult() throws Throwable {
        final String queueInput = "'";
        final char openMarker = ')';
        final char closeMarker = '$';

        TokenQueue queue = new TokenQueue(queueInput);

        String balancedText = queue.chompBalanced(openMarker, closeMarker);
        assertEquals(queueInput, balancedText);

        String elementSelector = queue.consumeElementSelector();
        assertFalse("The consumed selector should differ from the balanced text",
                elementSelector.equals((Object) balancedText));
    }
}
