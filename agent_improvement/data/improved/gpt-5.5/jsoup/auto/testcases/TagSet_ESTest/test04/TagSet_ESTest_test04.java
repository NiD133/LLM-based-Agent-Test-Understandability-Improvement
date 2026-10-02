package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test04 extends TagSet_ESTest_scaffolding {

    private static final String ORIGINAL_TAG_NAME = "v;;K-";
    private static final String PRESERVED_TAG_NAME = "J";
    private static final String NORMAL_NAME = "~m&2\"v*M>Y$C[<";
    private static final String EMPTY_NAMESPACE = "";

    @Test(timeout = 4000)
    public void test04() throws Throwable {
        TagSet tagSet = new TagSet();

        Tag lowerCaseTag = tagSet.valueOf(ORIGINAL_TAG_NAME, NORMAL_NAME, EMPTY_NAMESPACE, false);
        assertEquals(NORMAL_NAME, lowerCaseTag.localName());

        Tag casePreservedTag = tagSet.valueOf(PRESERVED_TAG_NAME, NORMAL_NAME, EMPTY_NAMESPACE, true);
        assertEquals(EMPTY_NAMESPACE, casePreservedTag.namespace());
        assertEquals(NORMAL_NAME, casePreservedTag.normalName());
        assertEquals(PRESERVED_TAG_NAME, casePreservedTag.name());
    }
}
