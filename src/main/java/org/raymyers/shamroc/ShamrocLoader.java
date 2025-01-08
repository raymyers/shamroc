package org.raymyers.shamroc;

import org.antlr.v4.runtime.*;
import org.raymyers.shamroc.ast.ShamrocAst;
import org.raymyers.shamroc.generated.ShamrocLexer;
import org.raymyers.shamroc.generated.ShamrocParser;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ShamrocLoader {
    private final List<String> syntaxErrors;
    private final ShamrocParser parser;
    private final ShamrocParser.ProgramContext startContext;

    public ShamrocLoader(File file) throws IOException {
        this(new ShamrocLexer(CharStreams.fromPath(file.toPath())));
    }
    public ShamrocLoader(String string) {
        this(new ShamrocLexer(CharStreams.fromString(string)));
    }
    public ShamrocLoader(InputStream stream) throws IOException {
        this(new ShamrocLexer(CharStreams.fromStream(stream)));
    }


    private ShamrocLoader(ShamrocLexer lexer) {
        this.parser = new ShamrocParser(new CommonTokenStream(lexer));

        ErrorListener errorListener = new ErrorListener();

        //Remove and Replace Default Console Error Listeners
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        lexer.addErrorListener(errorListener);

        syntaxErrors = errorListener.getSyntaxErrors();
        startContext = parser.program();
    }

    private class ErrorListener extends ConsoleErrorListener {

        private final List<String> syntaxErrors = new ArrayList<>();

        @Override
        public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
            syntaxErrors.add(String.format("Syntax Error on %s:%s - %s", line, charPositionInLine, msg));
        }

        public List<String> getSyntaxErrors(){
            return syntaxErrors;
        }
    }


    String getStringTree() {
        return startContext.toStringTree(parser);
    }

    public ShamrocAst.Program toAst() {
        return new ShamrocTransformer().transformProgram(startContext);
    }

    public boolean valid(){
        return syntaxErrors.size() == 0;
    }

    public String getSyntaxErrors(){
        return String.join("\n",syntaxErrors) + "\n";
    }
}
