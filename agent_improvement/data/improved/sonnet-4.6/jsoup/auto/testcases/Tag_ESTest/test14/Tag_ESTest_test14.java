package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test14 extends Tag_ESTest_scaffolding {

    // A Tag constructed directly with new Tag(name, namespace) is not added to any TagSet,
    // so the Known option bit is never set and isKnownTag() must return false.
    @Test(timeout = 4000)
    public void test_newTagWithEmptyNameAndNamespace_isNotKnown() throws Throwable {
        Tag emptyNameTag = new Tag("", "");
        boolean isKnown = emptyNameTag.isKnownTag();
        assertFalse(isKnown);
    }
}
