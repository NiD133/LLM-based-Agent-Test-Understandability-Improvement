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

    /**
     * Verifies that valueOf() correctly handles the preserveTagCase flag:
     * - When preserveTagCase=false the returned tag adopts the supplied normalName as its local name.
     * - When preserveTagCase=true and the normalName already exists in the set, the returned tag
     *   keeps the original (case-preserved) tagName while retaining the normalName and namespace.
     */
    @Test(timeout = 4000)
    public void test_valueOfPreservesOrNormalizesTagCaseCorrectly() throws Throwable {
        TagSet tagSet = new TagSet();

        // preserveTagCase=false: the tag is stored and returned using the normalName
        Tag normalizedTag = tagSet.valueOf("v;;K-", "~m&2\"v*M>Y$C[<", "", false);
        assertEquals("~m&2\"v*M>Y$C[<", normalizedTag.localName());

        // preserveTagCase=true: a tag whose normalName already exists in the set is cloned with
        // the case-preserved tagName "J", while keeping the original normalName and empty namespace
        Tag casePreservedTag = tagSet.valueOf("J", "~m&2\"v*M>Y$C[<", "", true);
        assertEquals("", casePreservedTag.namespace());
        assertEquals("~m&2\"v*M>Y$C[<", casePreservedTag.normalName());
        assertEquals("J", casePreservedTag.name());
    }
}
