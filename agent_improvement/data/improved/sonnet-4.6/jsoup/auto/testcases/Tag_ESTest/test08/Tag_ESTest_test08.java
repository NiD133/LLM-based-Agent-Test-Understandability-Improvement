package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test08 extends Tag_ESTest_scaffolding {

    // A Tag created directly via constructor (not via TagSet) has no options set,
    // so it is neither a known tag nor form-submittable.
    @Test(timeout = 4000)
    public void test_newTagWithEmptyNameAndNamespace_isNotKnownAndNotFormSubmittable() throws Throwable {
        Tag emptyTag = new Tag("", "");

        boolean isFormSubmittable = emptyTag.isFormSubmittable();

        assertFalse(emptyTag.isKnownTag());
        assertFalse(isFormSubmittable);
    }
}
