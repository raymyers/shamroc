package org.raymyers.shamroc;

import org.raymyers.shamroc.generated.SexprParser;

import java.util.List;

public class SexprTransformer {
    public SexprAst.Sexpr transformSexpr(SexprParser.SexprContext node) {
        List<SexprAst.Item> items = node.item().stream().map(this::transformItem).toList();
        return new SexprAst.Sexpr(items);
    }

    public SexprAst.Item transformItem(SexprParser.ItemContext node) {
        if (node.atom() != null) {
            return transformAtom(node.atom());
        }
        List<SexprAst.Item> items = node.list_().item().stream().map(this::transformItem).toList();
        return new SexprAst.List_(items);
    }

    public SexprAst.Atom transformAtom(SexprParser.AtomContext node) {
        if (node.NUMBER() != null) {
            return new SexprAst.NUMBER(node.NUMBER().getText());
        } else if (node.STRING() != null) {
            return new SexprAst.STRING(node.STRING().getText());
        } else if (node.SYMBOL() != null) {
            return new SexprAst.SYMBOL(node.SYMBOL().getText());
        }
        throw new RuntimeException("Unknown Atom");
    }
}
