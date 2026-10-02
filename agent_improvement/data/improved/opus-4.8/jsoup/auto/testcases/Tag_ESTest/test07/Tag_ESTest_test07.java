package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test07 extends Tag_ESTest_scaffolding {

    /**
     * Setting the FormSubmittable option on a tag should make isFormSubmittable() report true.
     * Setting any option also marks the tag as "known" (per Tag.set()), so isKnownTag() becomes true too.
     */
    @Test(timeout = 4000)
    public void settingFormSubmittableOptionMakesTagSubmittableAndKnown() throws Throwable {
        // Tag.FormSubmittable has the integer value 512 (1 << 9).
        Tag tag = Tag.valueOf("bZJf");
        tag.set(Tag.FormSubmittable);

        boolean isFormSubmittable = tag.isFormSubmittable();

        assertTrue("set() marks the tag as known", tag.isKnownTag());
        assertTrue("FormSubmittable option should be reported", isFormSubmittable);
    }
}
