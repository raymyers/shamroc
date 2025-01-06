package org.raymyers.shamroc;

import java.util.List;

public interface NanopassAst {
    record Program(List<Def> items) implements BaseAst { }

    sealed interface Def extends BaseAst permits DefLanguage { }

    record DefLanguage(String name) implements Def { }
}
