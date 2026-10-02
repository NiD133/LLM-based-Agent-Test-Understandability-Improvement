package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test19 extends Tag_ESTest_scaffolding {

    /**
     * A Tag created directly via the constructor (rather than through a TagSet)
     * carries no options, so it defaults to inline, unknown, keeps its given
     * namespace, and exposes a lower-cased normal name.
     */
    @Test(timeout = 4000)
    public void freshlyConstructedTagIsInlineAndUnknown() throws Throwable {
        String rawName = "'k7*}2H9fkf;cif_ ";
        Tag tag = new Tag(rawName, rawName);

        boolean inline = tag.isInline();

        assertTrue("A tag with no options should be inline", inline);
        assertFalse("A directly constructed tag should not be a known tag", tag.isKnownTag());
        assertEquals("The namespace should match the value passed in", rawName, tag.namespace());
        assertEquals("The normal name should be the trimmed, lower-cased tag name",
                "'k7*}2h9fkf;cif_", tag.normalName());
    }
}
