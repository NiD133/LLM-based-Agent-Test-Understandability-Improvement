package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test36 extends Tag_ESTest_scaffolding {

    /**
     * A Tag constructed directly (rather than through a TagSet) with an empty
     * name and empty namespace should report itself as not a known tag, because
     * no options have been set on it.
     */
    @Test(timeout = 4000)
    public void newTagWithEmptyNameAndNamespaceIsNotKnown() throws Throwable {
        Tag tag = new Tag("", "");

        String namespace = tag.namespace();

        assertEquals("", namespace);
        assertFalse(tag.isKnownTag());
    }
}
