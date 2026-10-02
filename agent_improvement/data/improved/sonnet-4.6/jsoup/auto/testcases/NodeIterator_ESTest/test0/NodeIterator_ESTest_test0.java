package org.jsoup.nodes;

import org.junit.Test;
import static org.junit.Assert.*;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.jsoup.parser.Parser;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test0 extends NodeIterator_ESTest_scaffolding {

    /**
     * Verifies that iterating over a document containing no FormElement nodes
     * reports hasNext() == false immediately, meaning the iterator is exhausted
     * from the start when the target type is absent from the tree.
     */
    @Test(timeout = 4000)
    public void test0() throws Throwable {
        // Build a document whose tree contains no FormElement nodes
        Document document = Parser.parse("g==i<Tb", "g==i<Tb");
        document.appendElement("g==i<Tb");

        // Filter the document tree for FormElement nodes
        NodeIterator<FormElement> iterator = new NodeIterator<FormElement>(document, FormElement.class);

        // No FormElement exists in the tree, so hasNext() must return false
        assertFalse(iterator.hasNext());
    }
}
