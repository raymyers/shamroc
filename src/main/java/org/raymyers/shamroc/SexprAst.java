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
//  lang Sexpr {
//    data Prog(List<Item> items);
//    data Item
//        : List_(List<Item> items)
//        | Atom;
//    data Atom
//        : record STRING(String v)
//        | record SYMBOL(String v)
//        | record NUMBER(String v);
// }
// lang Sexp2 extends Sexpr {
//   ~Atom : -Symbol;
// }
}
