package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test15 extends Entities_ESTest_scaffolding {

    /**
     * findPrefix returns the longest base named entity that is a prefix of the
     * input. When the input begins with no known entity name, it returns "".
     */
    @Test(timeout = 4000)
    public void findPrefixReturnsEmptyWhenInputHasNoEntityPrefix() throws Throwable {
        String inputWithoutEntityPrefix = "a%VaysnL|7L=rC";

        String longestMatchingPrefix = Entities.findPrefix(inputWithoutEntityPrefix);

        assertEquals("", longestMatchingPrefix);
    }
}
