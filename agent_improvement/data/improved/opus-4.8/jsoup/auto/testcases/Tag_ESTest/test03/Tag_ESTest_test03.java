package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test03 extends Tag_ESTest_scaffolding {

    /**
     * A cloned Tag should be equal to its original, since clone() copies all
     * fields (tagName, normalName, namespace, options). The clone is also not a
     * "known" tag, because it was created directly rather than registered in a
     * TagSet or modified via set()/clear().
     */
    @Test(timeout = 4000)
    public void cloneIsEqualToOriginalAndStaysUnknown() throws Throwable {
        String tagName = " |eOx/>T!";
        Tag originalTag = new Tag(tagName, tagName, tagName);

        Tag clonedTag = originalTag.clone();

        assertEquals("A tag should equal its clone", originalTag, clonedTag);
        assertFalse("A freshly created/cloned tag is not a known tag", clonedTag.isKnownTag());
    }
}
