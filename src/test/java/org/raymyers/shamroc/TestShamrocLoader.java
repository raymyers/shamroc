package org.raymyers.shamroc;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.raymyers.shamroc.ast.ShamrocAst;
import org.raymyers.shamroc.ast.ShamrocL2;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestShamrocLoader {

    public static final String EXAMPLE_FILE = "Shamroc.shamroc";

    @Test
    void basicParse() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream(EXAMPLE_FILE);
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

    @Test
    void transformToL2() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("Shamroc.shamroc");
        ShamrocLoader subject = new ShamrocLoader(stream);
//        System.out.println(subject.getSyntaxErrors());
        assertTrue(subject.valid());
        ShamrocAst.Program ast = subject.toAst();
        ShamrocL2.Program l2 = new ShamrocAstToL2().transformProgram(ast);
        Approvals.verify(l2);
    }
}
