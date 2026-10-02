package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test29 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that cloning a Tag produces an independent copy:
     * mutating the clone's options (setting the Data flag) does not
     * affect the original tag's known-tag status.
     */
    @Test(timeout = 4000)
    public void test29() throws Throwable {
        Tag originalTag = new Tag("", "");
        Tag clonedTag = originalTag.clone();

        // Tag.Data = 1 << 8 = 256; marks the tag as a data element (e.g. <script>)
        clonedTag.options = Tag.Data;
        clonedTag.textState(); // returns TokeniserState.Rawtext for a Data tag

        // The original tag must remain unaffected by changes to the clone
        assertFalse(originalTag.isKnownTag());
    }
}
