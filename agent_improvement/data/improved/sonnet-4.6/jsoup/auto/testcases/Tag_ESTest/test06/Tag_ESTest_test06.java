package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test06 extends Tag_ESTest_scaffolding {

    /**
     * Verifies that when the RcData static option flag is reassigned and a tag's options field is
     * set to match that value, textState() reads the updated flag correctly, and the tag's
     * namespace is preserved as originally set.
     */
    @Test(timeout = 4000)
    public void test06() throws Throwable {
        // "Oc2" is not a standard HTML tag name, so the resulting tag should be unknown
        ParseSettings htmlDefaultSettings = ParseSettings.htmlDefault;
        Tag unknownTag = Tag.valueOf("Oc2", "Oc2", htmlDefaultSettings);
        assertFalse(unknownTag.isKnownTag());

        // Reassign the RcData static flag to 512 and mirror that value in the tag's options field,
        // so that is(RcData) returns true and textState() returns TokeniserState.Rcdata
        Tag.RcData = 512;
        unknownTag.options = 512;
        unknownTag.textState();

        // The namespace supplied at creation ("Oc2") must be returned unchanged
        assertEquals("Oc2", unknownTag.namespace());
    }
}
