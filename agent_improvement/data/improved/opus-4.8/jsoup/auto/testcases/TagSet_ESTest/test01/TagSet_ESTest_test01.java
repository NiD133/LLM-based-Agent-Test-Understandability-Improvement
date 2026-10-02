package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test01 extends TagSet_ESTest_scaffolding {

    /**
     * A TagSet's equals() should be reflexive: a TagSet is always equal to itself.
     */
    @Test(timeout = 4000)
    public void htmlTagSetEqualsItself() throws Throwable {
        TagSet htmlTagSet = TagSet.Html();

        boolean isEqualToItself = htmlTagSet.equals(htmlTagSet);

        assertTrue("A TagSet should be equal to itself", isEqualToItself);
    }
}
