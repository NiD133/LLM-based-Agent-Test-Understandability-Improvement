package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class TagSet_ESTest_test03 extends TagSet_ESTest_scaffolding {

    /**
     * When two tags are requested with different raw tag names but the SAME normal name,
     * {@link TagSet#valueOf} resolves the second request back to the Tag already cached
     * under that shared normal name (case is not preserved, so lookup happens by normal name).
     * The two calls therefore return the very same Tag instance.
     */
    @Test(timeout = 4000)
    public void valueOf_returnsSameTagWhenNormalNameMatches() throws Throwable {
        // A shared normal name used for two different raw tag names.
        String sharedNormalName = "~m&2\"v*M>Y$C[<";
        String emptyNamespace = "";
        boolean preserveTagCase = false;

        TagSet tagSet = new TagSet();

        // First lookup creates and caches a new Tag keyed by the shared normal name.
        Tag firstTag = tagSet.valueOf("v;;K-", sharedNormalName, emptyNamespace, preserveTagCase);

        // Second lookup uses a different raw name but the same normal name,
        // so it resolves to the already-cached Tag instead of creating a new one.
        Tag secondTag = tagSet.valueOf("J", sharedNormalName, emptyNamespace, preserveTagCase);

        assertSame(secondTag, firstTag);
        assertEquals(sharedNormalName, secondTag.normalName());
    }
}
