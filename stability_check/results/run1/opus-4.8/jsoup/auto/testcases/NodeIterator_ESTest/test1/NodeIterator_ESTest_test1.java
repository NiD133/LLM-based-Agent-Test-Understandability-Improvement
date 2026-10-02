package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test1 extends NodeIterator_ESTest_scaffolding {

    /**
     * A NodeIterator filtered to FormElement should report no matching nodes
     * when the parsed document (and its clone) contains no form elements.
     */
    @Test(timeout = 4000)
    public void hasNextIsFalseWhenNoNodeMatchesFilterType() throws Throwable {
        // Parse text that produces a document without any form elements.
        Document document = Parser.parse("kv3=Q", "");
        Element clonedDocument = document.doClone(document);

        // Iterate the cloned tree, keeping only FormElement nodes.
        NodeIterator<FormElement> formElementIterator =
                new NodeIterator<FormElement>(clonedDocument, FormElement.class);

        // No form elements exist, so there is nothing to iterate.
        assertFalse(formElementIterator.hasNext());
    }
}
