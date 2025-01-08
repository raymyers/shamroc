package org.raymyers.shamroc.ast;

import java.util.List;
import java.util.Optional;

public interface ShamrocAst {
    record Program(List<Def> defs) {}

    sealed interface Def permits LangDef, SequenceDef {}

    record LangDef(String name, List<LangItem> langItems) implements Def {}
    record SequenceDef(String name) implements Def {}

    sealed interface LangItem permits Record, SumType {}

    record Record(String name, List<RecordField> recordFields) implements LangItem, SumTypeOption {}

    record RecordField(List<NameOrType> nameOrTypes) {}

    record SumType(String name, List<SumTypeOption> sumTypeOptions) implements LangItem {}

    sealed interface SumTypeOption permits Record, NameOrType {}

    record NameOrType(Ident ident1, Optional<Ident> ident2) implements SumTypeOption {}

    record Ident(String string) {}
}
