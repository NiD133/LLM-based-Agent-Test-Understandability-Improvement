package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test06 extends Tag_ESTest_scaffolding {

    /**
     * A tag created via valueOf for an unrecognised name is not a known tag, and
     * resolving its text tokeniser state leaves the tag's namespace untouched.
     */
    @Test(timeout = 4000)
    public void unknownTagKeepsNamespaceAfterResolvingTextState() throws Throwable {
        String tagAndNamespace = "Oc2";

        Tag tag = Tag.valueOf(tagAndNamespace, tagAndNamespace, ParseSettings.htmlDefault);
        assertFalse("a generic, undefined tag should not be reported as known", tag.isKnownTag());

        // Mark this tag as carrying RCDATA content so textState() resolves a state.
        Tag.RcData = 512;
        tag.options = 512;
        tag.textState();

        assertEquals("namespace should be unchanged by textState()", "Oc2", tag.namespace());
    }
}
