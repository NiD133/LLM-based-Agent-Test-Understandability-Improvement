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
public class TagSet_ESTest_test03 extends TagSet_ESTest_scaffolding {

    private static final String FIRST_TAG_NAME = "v;;K-";
    private static final String SECOND_TAG_NAME = "J";
    private static final String SHARED_NORMAL_NAME = "~m&2\"v*M>Y$C[<";
    private static final String EMPTY_NAMESPACE = "";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        TagSet tagSet = new TagSet();

        Tag firstLookup = tagSet.valueOf(FIRST_TAG_NAME, SHARED_NORMAL_NAME, EMPTY_NAMESPACE, false);
        Tag secondLookup = tagSet.valueOf(SECOND_TAG_NAME, SHARED_NORMAL_NAME, EMPTY_NAMESPACE, false);

        assertSame(secondLookup, firstLookup);
        assertEquals(SHARED_NORMAL_NAME, secondLookup.normalName());
    }
}
