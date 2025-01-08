package org.raymyers.shamroc;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestFoo {
    @Test
    void basicParse()
    {
        SexprLoader subject = new SexprLoader("""
        (if a (if b c))
        """);
        assertTrue(subject.valid());
        Approvals.verify(subject.getStringTree());
    }
}
