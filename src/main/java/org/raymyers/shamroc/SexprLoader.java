package org.raymyers.shamroc;

import org.antlr.v4.runtime.*;
import org.raymyers.shamroc.generated.SexprLexer;
import org.raymyers.shamroc.generated.SexprParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SexprLoader {
    private final List<String> syntaxErrors;
    private final SexprParser parser;
    private final SexprParser.SexprContext startContext;

    public SexprLoader(File file) throws IOException {
        this(new SexprLexer(CharStreams.fromPath(file.toPath())));
    }
    public SexprLoader(String input){
        this(new SexprLexer(CharStreams.fromString(input)));
    }


    private SexprLoader(SexprLexer lexer) {
        this.parser = new SexprParser(new CommonTokenStream(lexer));

        ErrorListener errorListener = new ErrorListener();

        //Remove and Replace Default Console Error Listeners
        lexer.removeErrorListeners();
        parser.removeErrorListeners();
        parser.addErrorListener(errorListener);
        lexer.addErrorListener(errorListener);

        syntaxErrors = errorListener.getSyntaxErrors();
        startContext = parser.sexpr();
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

    public SexprAst.Sexpr toAst() {
        return new SexprTransformer().transformSexpr(startContext);
    }

    public boolean valid(){
        return syntaxErrors.size() == 0;
    }

    public String getSyntaxErrors(){
        return String.join("\n",syntaxErrors) + "\n";
    }
}
