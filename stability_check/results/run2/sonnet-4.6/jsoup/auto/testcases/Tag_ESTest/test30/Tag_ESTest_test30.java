package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test30 extends Tag_ESTest_scaffolding {

    /**
     * A Tag constructed directly with an empty name and empty namespace is not a known tag.
     * Known tags are only those registered via a TagSet; direct construction does not set
     * the Known flag. textState() is called to exercise the method (it returns null because
     * neither RcData nor Data options are set), and then isKnownTag() confirms no Known flag was set.
     */
    @Test(timeout = 4000)
    public void test30() throws Throwable {
        Tag emptyNameTag = new Tag("", "");

        // textState() returns null when neither RcData nor Data options are set
        emptyNameTag.textState();

        // A directly constructed Tag is not added to any TagSet, so it is not a known tag
        assertFalse(emptyNameTag.isKnownTag());
    }
}
