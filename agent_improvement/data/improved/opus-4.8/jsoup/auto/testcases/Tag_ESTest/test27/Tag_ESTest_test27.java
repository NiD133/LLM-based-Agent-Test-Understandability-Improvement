package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test27 extends Tag_ESTest_scaffolding {

    /**
     * An unknown tag (not pre-defined in the TagSet) starts out as "not known".
     * Clearing any option other than {@code Tag.Known} touches the tag and therefore
     * marks it as known, per {@link Tag#clear(int)}.
     */
    @Test(timeout = 4000)
    public void clearingNonKnownOptionMarksTagAsKnown() throws Throwable {
        Tag unknownTag = Tag.valueOf("bZJf");
        assertFalse("A freshly created unknown tag should not be known", unknownTag.isKnownTag());

        // Tag.FormSubmittable == 512 (1 << 9); clearing it is enough to "touch" the tag.
        unknownTag.clear(Tag.FormSubmittable);

        assertTrue("Clearing a non-Known option should mark the tag as known", unknownTag.isKnownTag());
    }
}
