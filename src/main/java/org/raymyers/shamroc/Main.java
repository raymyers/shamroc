package org.raymyers.shamroc;

public class Main {
    public static void main(String[] args) {
        String sexpr = """
            (if a b)
        """;
        SexprAst.Sexpr ast = new SexprLoader(sexpr).toAst();
        System.out.println(ast);
    }
}