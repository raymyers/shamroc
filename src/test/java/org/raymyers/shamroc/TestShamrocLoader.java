package org.raymyers.shamroc;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestShamrocLoader {
    @Test
    void basicParse() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("Shamroc.shamroc");
        ShamrocLoader subject = new ShamrocLoader(stream);
//        System.out.println(subject.getSyntaxErrors());
        assertTrue(subject.valid());
        Approvals.verify(subject.getStringTree());
    }

    @Test
    void transformToAst() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("Shamroc.shamroc");
        ShamrocLoader subject = new ShamrocLoader(stream);
//        System.out.println(subject.getSyntaxErrors());
        assertTrue(subject.valid());
        Approvals.verify(subject.toAst());
    }
}
