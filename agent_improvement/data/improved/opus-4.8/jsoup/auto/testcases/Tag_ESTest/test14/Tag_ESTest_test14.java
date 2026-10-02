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

    /**
     * A Tag that is freshly constructed (and never added to a TagSet or had any
     * option applied) should not be reported as a known tag.
     */
    @Test(timeout = 4000)
    public void freshlyConstructedTagIsNotKnown() throws Throwable {
        Tag freshTag = new Tag("", "");

        boolean known = freshTag.isKnownTag();

        assertFalse(known);
    }
}
