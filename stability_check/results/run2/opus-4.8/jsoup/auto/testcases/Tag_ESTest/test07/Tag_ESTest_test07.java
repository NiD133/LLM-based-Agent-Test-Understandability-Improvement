package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test07 extends Tag_ESTest_scaffolding {

    /**
     * Setting the FormSubmittable option (1 << 9 == 512) should make
     * isFormSubmittable() return true, and since setting any option marks the
     * tag as "known", isKnownTag() should also return true.
     */
    @Test(timeout = 4000)
    public void settingFormSubmittableOptionMarksTagSubmittableAndKnown() throws Throwable {
        Tag tag = Tag.valueOf("bZJf");

        tag.set(Tag.FormSubmittable);

        assertTrue("tag should be form-submittable after setting the option",
            tag.isFormSubmittable());
        assertTrue("setting any option marks the tag as known",
            tag.isKnownTag());
    }
}
