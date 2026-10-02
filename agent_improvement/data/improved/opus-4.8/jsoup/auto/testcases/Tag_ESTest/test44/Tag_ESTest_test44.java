package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test44 extends Tag_ESTest_scaffolding {

    /**
     * When a tag is created with case preservation enabled, its name and namespace
     * keep the original casing while {@link Tag#normalName()} returns the lowercased form.
     * An unknown custom tag like "MJ" is not flagged as a known tag.
     */
    @Test(timeout = 4000)
    public void unknownTagPreservesCaseButNormalNameIsLowercased() throws Throwable {
        ParseSettings caseSensitiveSettings = ParseSettings.preserveCase;

        Tag mjTag = Tag.valueOf("MJ", "MJ", caseSensitiveSettings);

        assertEquals("mj", mjTag.normalName());
        assertEquals("MJ", mjTag.namespace());
        assertFalse(mjTag.isKnownTag());
    }
}
