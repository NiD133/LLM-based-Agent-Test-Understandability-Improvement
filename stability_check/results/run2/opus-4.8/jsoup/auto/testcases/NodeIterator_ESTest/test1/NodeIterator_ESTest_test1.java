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
     * A NodeIterator filtered by a type that is absent from the tree should
     * report no elements. Here the parsed document contains no FormElement,
     * so iterating for FormElement yields nothing.
     */
    @Test(timeout = 4000)
    public void hasNextIsFalseWhenNoNodeMatchesFilterType() throws Throwable {
        Document document = Parser.parse("kv3=Q", "");
        Element clonedDocument = document.doClone(document);

        NodeIterator<FormElement> formElementIterator =
                new NodeIterator<FormElement>(clonedDocument, FormElement.class);

        assertFalse(formElementIterator.hasNext());
    }
}
