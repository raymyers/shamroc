package org.raymyers.shamroc;

import org.raymyers.shamroc.ast.ShamrocAst;
import org.raymyers.shamroc.ast.ShamrocL2;
import org.raymyers.shamroc.generated.ShamrocParser;

import java.util.List;
import java.util.Optional;

public class ShamrocAstToL2 {
    public ShamrocL2.Program transformProgram(ShamrocAst.Program node) {
        List<ShamrocL2.LangDef> langDefs = node.defs().stream()
                .filter(a -> a instanceof ShamrocAst.LangDef)
                .map(a -> transformLangDef((ShamrocAst.LangDef)a)).toList();
        List<ShamrocL2.SequenceDef> sequenceDefs = node.defs().stream()
                .filter(a -> a instanceof ShamrocAst.SequenceDef)
                .map(a -> transformSequenceDef((ShamrocAst.SequenceDef)a)).toList();
        return new ShamrocL2.Program(langDefs, sequenceDefs);
    }

    private ShamrocL2.SequenceDef transformSequenceDef(ShamrocAst.SequenceDef node) {
        return new ShamrocL2.SequenceDef(node.name());
    }

    private ShamrocL2.LangDef transformLangDef(ShamrocAst.LangDef node) {
        return new ShamrocL2.LangDef(node.name(), node.langItems().stream().map(this::transformLangItem).toList());
    }

    private ShamrocL2.LangItem transformLangItem(ShamrocAst.LangItem node) {
        return switch (node) {
            case ShamrocAst.Record rec ->
                    transformRecord(rec);
            case ShamrocAst.SumType sumType ->
                    new ShamrocL2.SumType(sumType.name(),
                            sumType.sumTypeOptions().stream().map(this::transformSumTypeOption).toList());
        };
    }

    private ShamrocL2.Record transformRecord(ShamrocAst.Record rec) {
        return new ShamrocL2.Record(rec.name(),
                rec.recordFields().stream().map(this::transformRecordField).toList());
    }

    private ShamrocL2.RecordField transformRecordField(ShamrocAst.RecordField recordField) {
        List<ShamrocL2.TypedIdent> typedIdents = recordField.nameOrTypes().stream().map(this::transformTypedIdent).toList();
        return new ShamrocL2.RecordField(typedIdents);
    }

    private ShamrocL2.TypedIdent transformTypedIdent(ShamrocAst.NameOrType a) {
        return a.ident2().isPresent() ?
                new ShamrocL2.TypedIdent(transformTypeToVarName(a.ident1()), new ShamrocL2.Type(a.ident2().get().string(), List.of()))
                : inferNamedType(a.ident1());
    }

    private ShamrocL2.TypedIdent inferNamedType(ShamrocAst.Ident id) {
        return new ShamrocL2.TypedIdent(transformTypeToVarName(id), new ShamrocL2.Type(id.string(), List.of()));
    }

    static ShamrocL2.Ident transformTypeToVarName(ShamrocAst.Ident ident) {
        // TODO
        return new ShamrocL2.Ident(ident.string().toLowerCase());
    }

    private ShamrocL2.SumTypeOption transformSumTypeOption(ShamrocAst.SumTypeOption node) {
        return switch (node) {
            case ShamrocAst.NameOrType nameOrType ->
                    transformTypedIdent(nameOrType);
            case ShamrocAst.Record record ->
                    transformRecord(record);
        };
    }
//
//    private ShamrocAst.SumType transformSumType(ShamrocParser.SumTypeContext sumType) {
//        String name = sumType.ID().getText();
//        var options = sumType.sumTypeOption().stream()
//                .map(this::transformSumTypeOption).toList();
//        return new ShamrocAst.SumType(name, options);
//    }

//    private ShamrocAst.Record transformRecord(ShamrocParser.RecordContext record) {
//        String name = record.ID().getText();
//        var recordFields = record.recordField().stream()
//                .map(this::transformRecordFields).toList();
//        return new ShamrocAst.Record(name, recordFields);
//    }

//    private ShamrocAst.SumTypeOption transformSumTypeOption(ShamrocParser.SumTypeOptionContext node) {
//        return node.record() == null
//                ? transformNameOrType(node.nameOrType())
//                : transformRecord(node.record());
//    }
//
//    private ShamrocAst.NameOrType transformNameOrType(ShamrocParser.NameOrTypeContext node) {
//        Optional<ShamrocAst.Ident> id2 = node.ID().size() > 1 ? Optional.of(new ShamrocAst.Ident(node.ID().get(1).getText())) : Optional.empty();
//        ShamrocAst.Ident id1 = new ShamrocAst.Ident(node.ID().get(0).getText());
//        return new ShamrocAst.NameOrType(id1, id2);
//    }

}
