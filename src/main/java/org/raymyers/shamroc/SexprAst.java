package org.raymyers.shamroc;

import java.util.List;

public interface SexprAst {
    record Sexpr(List<Item> items) implements BaseAst {
    }
    sealed interface Item extends BaseAst permits List_, Atom { }

    record List_(
            List<Item> items
    ) implements Item {}

    sealed interface Atom
            extends Item
            permits STRING, SYMBOL, NUMBER, DOT { }
    record STRING(String v) implements Atom { }
    record SYMBOL(String v) implements Atom { }
    record NUMBER(String v) implements Atom { }
    record DOT() implements Atom { }

}
