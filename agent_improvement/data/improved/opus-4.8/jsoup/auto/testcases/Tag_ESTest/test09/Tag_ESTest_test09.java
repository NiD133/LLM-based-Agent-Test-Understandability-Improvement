package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test09 extends Tag_ESTest_scaffolding {

    /**
     * Setting the PreserveWhitespace option on a tag should:
     *   - make preserveWhitespace() report true, and
     *   - mark the tag as known (set() always sets the Known flag).
     *
     * The literal 64 used by the original test is Tag.PreserveWhitespace (1 << 6).
     */
    @Test(timeout = 4000)
    public void settingPreserveWhitespaceEnablesItAndMarksTagKnown() throws Throwable {
        Tag tag = Tag.valueOf("bZJf");

        Tag updatedTag = tag.set(Tag.PreserveWhitespace);

        assertTrue("preserveWhitespace() should be true after setting the option",
                updatedTag.preserveWhitespace());
        assertTrue("set() should mark the tag as a known tag",
                tag.isKnownTag());
    }
}
