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
public class TagSet_ESTest_test09 extends TagSet_ESTest_scaffolding {

    // A mixed-case tag name used as both the tag name and namespace.
    // valueOf() with preserveCase settings keeps the original casing for toString()
    // but normalName() returns the lowercased form.
    private static final String MIXED_CASE_TAG = "~m&2\"v*M>Y$C[<";
    private static final String NORMALIZED_TAG  = "~m&2\"v*m>y$c[<";

    @Test(timeout = 4000)
    public void test09_valueOfPreservesCaseAndTagSetCopyIsEqual() throws Throwable {
        // Part 1: valueOf() with preserveCase preserves the original tag name and lowercases normalName.
        TagSet tagSet0 = new TagSet();
        Tag tag0 = tagSet0.valueOf(MIXED_CASE_TAG, MIXED_CASE_TAG);

        assertNotNull(tag0);
        assertEquals(NORMALIZED_TAG,  tag0.normalName());
        assertEquals(MIXED_CASE_TAG,  tag0.toString());
        assertEquals(MIXED_CASE_TAG,  tag0.namespace());

        // Part 2: A TagSet copy constructed from another TagSet compares equal to it.
        TagSet tagSet1 = new TagSet(tagSet0);
        assertTrue(tagSet1.equals((Object) tagSet0));
    }
}
