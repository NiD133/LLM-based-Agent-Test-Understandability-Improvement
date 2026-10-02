package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test00 extends TagSet_ESTest_scaffolding {

    /**
     * TagSet.equals returns false when compared against an object that is not a
     * TagSet, since equals first checks {@code o instanceof TagSet}.
     */
    @Test(timeout = 4000)
    public void equalsReturnsFalseForNonTagSetObject() throws Throwable {
        TagSet tagSet = new TagSet();
        Object nonTagSet = new Object();

        boolean isEqual = tagSet.equals(nonTagSet);

        assertFalse(isEqual);
    }
}
