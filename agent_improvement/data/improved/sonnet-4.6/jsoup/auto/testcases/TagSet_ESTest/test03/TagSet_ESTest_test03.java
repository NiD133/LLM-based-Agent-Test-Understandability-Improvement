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

    // Two distinct raw tag names that share the same pre-computed normalName.
    // When preserveTagCase=false, valueOf() stores and looks up tags by normalName,
    // so both calls resolve to the identical Tag instance.
    private static final String RAW_TAG_NAME_FIRST  = "v;;K-";
    private static final String RAW_TAG_NAME_SECOND = "J";
    private static final String SHARED_NORMAL_NAME  = "~m&2\"v*M>Y$C[<";
    private static final String NAMESPACE           = "";

    @Test(timeout = 4000)
    public void test03() throws Throwable {
        TagSet tagSet = new TagSet();

        // First call: creates a new Tag stored under SHARED_NORMAL_NAME (preserveTagCase=false
        // means the effective key is the normalName, not the raw tagName).
        Tag firstTag = tagSet.valueOf(RAW_TAG_NAME_FIRST, SHARED_NORMAL_NAME, NAMESPACE, false);

        // Second call: different raw tagName, but same normalName → hits the cached entry.
        Tag secondTag = tagSet.valueOf(RAW_TAG_NAME_SECOND, SHARED_NORMAL_NAME, NAMESPACE, false);

        // Both lookups must return the exact same Tag object.
        assertSame(secondTag, firstTag);

        // The tag's normalName must equal the shared normal name used during creation.
        assertEquals(SHARED_NORMAL_NAME, secondTag.normalName());
    }
}
