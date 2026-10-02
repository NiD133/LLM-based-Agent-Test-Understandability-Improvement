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
     * When the parsed tree contains no node of the requested type, a NodeIterator
     * filtered to that type should report that it has no elements to return.
     */
    @Test(timeout = 4000)
    public void hasNextIsFalseWhenNoNodeMatchesFilterType() throws Throwable {
        // Parse some markup that contains no <form> element.
        Document document = Parser.parse("kv3=Q", "");
        Element clonedRoot = document.doClone(document);

        // Iterate the tree, keeping only FormElement nodes (of which there are none).
        NodeIterator<FormElement> formElementIterator =
                new NodeIterator<FormElement>(clonedRoot, FormElement.class);

        assertFalse(formElementIterator.hasNext());
    }
}
