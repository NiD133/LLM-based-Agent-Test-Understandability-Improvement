package org.jsoup.nodes;

import org.junit.Test;
import static org.evosuite.shaded.org.mockito.Mockito.*;
import java.util.function.Consumer;
import org.evosuite.runtime.EvoRunner;
import org.evosuite.runtime.EvoRunnerParameters;
import org.evosuite.runtime.ViolatedAssumptionAnswer;
import org.junit.runner.RunWith;

@RunWith(EvoRunner.class)
@EvoRunnerParameters(mockJVMNonDeterminism = true, useVFS = true, useVNET = true, resetStaticState = true, separateClassLoader = false)
public class NodeIterator_ESTest_test3 extends NodeIterator_ESTest_scaffolding {

    /**
     * Verifies that iterating with forEachRemaining is safe after the iterator's
     * starting node has already been removed from the tree.
     *
     * The NodeIterator is created over an element but filtered to only emit
     * FormElement nodes. Since the appended element is not a FormElement, the
     * iterator has nothing to yield. Calling remove() detaches the start node,
     * and forEachRemaining must then complete without ever invoking the consumer.
     */
    @Test(timeout = 4000)
    public void forEachRemainingAfterRemovingStartNodeDoesNothing() throws Throwable {
        Document document = new Document("Gz-OTe\"24", "Wo8Lgsblf?");
        Element startElement = document.appendElement("org.jsoup.nodes.NodeIterator");

        // Iterator filters for FormElement, but startElement is a plain Element,
        // so the iterator yields no matching nodes.
        NodeIterator<FormElement> iterator =
                new NodeIterator<FormElement>(startElement, FormElement.class);

        // Detach the iterator's current (start) node from the document.
        iterator.remove();

        // With no remaining matching nodes, the consumer is never called.
        @SuppressWarnings("unchecked")
        Consumer<Object> consumer = (Consumer<Object>) mock(Consumer.class, new ViolatedAssumptionAnswer());
        iterator.forEachRemaining(consumer);
    }
}
