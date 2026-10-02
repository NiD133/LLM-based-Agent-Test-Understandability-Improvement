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

    @Test(timeout = 4000)
    public void test07_settingFormSubmittableOptionMarksTagAsKnownAndSubmittable() throws Throwable {
        Tag customTag = Tag.valueOf("bZJf");
        customTag.set(Tag.FormSubmittable);

        boolean isFormSubmittable = customTag.isFormSubmittable();

        assertTrue(customTag.isKnownTag());
        assertTrue(isFormSubmittable);
    }
}
