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

    private static final String CUSTOM_TAG_NAME = "bZJf";
    private static final int FORM_SUBMITTABLE_OPTION = 512;

    @Test(timeout = 4000)
    public void test07() throws Throwable {
        Tag customTag = Tag.valueOf(CUSTOM_TAG_NAME);

        customTag.set(FORM_SUBMITTABLE_OPTION);
        boolean isFormSubmittable = customTag.isFormSubmittable();

        assertTrue("Setting any option marks an unknown tag as known", customTag.isKnownTag());
        assertTrue("The form-submittable option should be enabled", isFormSubmittable);
    }
}
