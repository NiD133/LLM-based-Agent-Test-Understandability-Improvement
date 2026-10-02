package org.jsoup.parser;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.evosuite.runtime.EvoAssertions.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class Tag_ESTest_test31 extends Tag_ESTest_scaffolding {

    private static final String CUSTOM_TAG_NAME = "<KN-^%U";
    private static final String HTML_NAMESPACE = "http://www.w3.org/1999/xhtml";
    private static final String NORMALIZED_CUSTOM_TAG_NAME = "<kn-^%u";

    @Test(timeout = 4000)
    public void test31() throws Throwable {
        Tag customTag = new Tag(CUSTOM_TAG_NAME);
        String localName = customTag.localName();

        assertEquals("New tags use the HTML namespace by default", HTML_NAMESPACE, customTag.namespace());
        assertFalse("A directly constructed custom tag is not a known tag", customTag.isKnownTag());
        assertEquals("Tags without a prefix use their full tag name as the local name", CUSTOM_TAG_NAME, localName);
        assertEquals("Normal names are lower-cased", NORMALIZED_CUSTOM_TAG_NAME, customTag.normalName());
    }
}
