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
     * Verifies that applying the {@code FormSubmittable} option to a custom tag
     * makes {@link Tag#isFormSubmittable()} report true, and that touching the
     * tag's options via {@link Tag#set(int)} also marks it as a known tag.
     */
    @Test(timeout = 4000)
    public void settingFormSubmittableOptionMakesTagSubmittableAndKnown() throws Throwable {
        Tag customTag = Tag.valueOf("bZJf");

        // Tag.FormSubmittable == 512 (1 << 9); set() also flags the tag as known.
        customTag.set(Tag.FormSubmittable);

        boolean formSubmittable = customTag.isFormSubmittable();
        assertTrue("Tag should be a known tag once an option has been set", customTag.isKnownTag());
        assertTrue("Tag should be form submittable after setting FormSubmittable", formSubmittable);
    }
}
