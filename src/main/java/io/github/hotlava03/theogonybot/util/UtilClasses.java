package io.github.hotlava03.theogonybot.util;

import io.github.hotlava03.theogonybot.TheogonyBot;

public class UtilClasses {
    private final HelpGen helpGen;
    private final EvalHandler evalHandler;

    public UtilClasses(TheogonyBot bot) {
        this.helpGen = new HelpGen(bot);
        this.evalHandler = new EvalHandler();
    }

    public HelpGen getHelpGen() {
        return this.helpGen;
    }

    public EvalHandler getEvalHandler() {
        return evalHandler;
    }
}
