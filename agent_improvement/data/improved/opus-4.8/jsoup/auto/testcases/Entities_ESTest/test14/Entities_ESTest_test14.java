package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Entities_ESTest_test14 extends Entities_ESTest_scaffolding {

    /**
     * findPrefix returns the longest base named entity that is a prefix of the input.
     * Since "shy" is itself a base named entity, it is its own longest matching prefix.
     */
    @Test(timeout = 4000)
    public void findPrefixReturnsInputWhenItIsAnEntityName() throws Throwable {
        String longestEntityPrefix = Entities.findPrefix("shy");

        assertEquals("shy", longestEntityPrefix);
    }
}
