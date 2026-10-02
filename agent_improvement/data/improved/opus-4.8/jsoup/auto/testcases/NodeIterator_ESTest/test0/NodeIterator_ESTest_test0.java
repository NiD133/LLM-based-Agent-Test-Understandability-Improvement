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
     * A NodeIterator filtered to FormElement should report no next node when the
     * document tree contains no FormElement instances.
     */
    @Test(timeout = 4000)
    public void hasNextReturnsFalseWhenNoNodeMatchesFilterType() throws Throwable {
        Document document = Parser.parse("g==i<Tb", "g==i<Tb");
        document.appendElement("g==i<Tb");

        NodeIterator<FormElement> formElementIterator =
                new NodeIterator<FormElement>(document, FormElement.class);

        boolean hasNext = formElementIterator.hasNext();

        assertFalse(hasNext);
    }
}
