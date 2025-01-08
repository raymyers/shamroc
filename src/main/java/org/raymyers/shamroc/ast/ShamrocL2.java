package org.raymyers.shamroc.ast;

import java.util.List;
import java.util.Optional;

public interface ShamrocL2 {
    record Program(
            List<LangDef> langDefs,
            List<SequenceDef> sequenceDefs
    ) {}
    record LangDef(String name, List<LangItem> langItems) {}
    record SequenceDef(String name) {}

    sealed interface LangItem permits Record, SumType {}

    record Record(String name, List<RecordField> recordFields) implements LangItem, SumTypeOption {}

    record RecordField(List<TypedIdent> nameOrTypes) {}

    record SumType(String name, List<SumTypeOption> sumTypeOptions) implements LangItem {}

    sealed interface SumTypeOption permits Record, TypedIdent {}

    record TypedIdent(Ident name, Type type) implements SumTypeOption {}

    record Ident(String string) {}

    record Type(String name, List<Type> typeParam) {}
}
