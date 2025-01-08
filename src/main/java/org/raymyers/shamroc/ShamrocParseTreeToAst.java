package org.raymyers.shamroc;

import org.raymyers.shamroc.ast.ShamrocAst;
import org.raymyers.shamroc.generated.ShamrocParser;

import java.util.List;
import java.util.Optional;

public class ShamrocParseTreeToAst {
    public ShamrocAst.Program transformProgram(ShamrocParser.ProgramContext node) {
        List<ShamrocAst.Def> items = node.def().stream().map(this::transformDef).toList();
        return new ShamrocAst.Program(items);
    }

    public ShamrocAst.Def transformDef(ShamrocParser.DefContext node) {
        ShamrocParser.DefLangContext defLang = node.defLang();
        if (node.defLang() != null) {
            String name = defLang.ID().getText();
            List<ShamrocAst.LangItem> langItems = defLang.langItem().stream().map(this::transformLangItem).toList();
            return new ShamrocAst.LangDef(name, langItems);
        } else {
            return new ShamrocAst.SequenceDef(node.defSequence().ID().getText());
        }

    }

    private ShamrocAst.LangItem transformLangItem(ShamrocParser.LangItemContext node) {

        if (null != node.record()) {
            ShamrocParser.RecordContext record = node.record();
            return transformRecord(record);
        } else {
            ShamrocParser.SumTypeContext sumType = node.sumType();
            return transformSumType(sumType);
        }
    }

    private ShamrocAst.SumType transformSumType(ShamrocParser.SumTypeContext sumType) {
        String name = sumType.ID().getText();
        var options = sumType.sumTypeOption().stream()
                .map(this::transformSumTypeOption).toList();
        return new ShamrocAst.SumType(name, options);
    }

    private ShamrocAst.Record transformRecord(ShamrocParser.RecordContext record) {
        String name = record.ID().getText();
        var recordFields = record.recordField().stream()
                .map(this::transformRecordFields).toList();
        return new ShamrocAst.Record(name, recordFields);
    }

    private ShamrocAst.SumTypeOption transformSumTypeOption(ShamrocParser.SumTypeOptionContext node) {
        return node.record() == null
                ? transformNameOrType(node.nameOrType())
                : transformRecord(node.record());
    }

    private ShamrocAst.NameOrType transformNameOrType(ShamrocParser.NameOrTypeContext node) {
        Optional<ShamrocAst.Ident> id2 = node.ID().size() > 1 ? Optional.of(new ShamrocAst.Ident(node.ID().get(1).getText())) : Optional.empty();
        ShamrocAst.Ident id1 = new ShamrocAst.Ident(node.ID().get(0).getText());
        return new ShamrocAst.NameOrType(id1, id2);
    }

    private ShamrocAst.RecordField transformRecordFields(ShamrocParser.RecordFieldContext node) {
        return new ShamrocAst.RecordField(node.nameOrType().stream().map(this::transformNameOrType).toList());
    }

}
