package org.jsoup.parser;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test10 extends TagSet_ESTest_scaffolding {

    /**
     * An empty TagSet should support hashCode() without throwing.
     * The empty tag map yields a stable hash, so the call simply completes normally.
     */
    @Test(timeout = 4000)
    public void emptyTagSetSupportsHashCode() throws Throwable {
        TagSet emptyTagSet = new TagSet();

        emptyTagSet.hashCode();
    }
}
