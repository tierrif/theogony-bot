package io.github.hotlava03.theogonybot;

import io.github.hotlava03.baclavaframework.CommandRegisterer;
import io.github.hotlava03.theogonybot.commands.basic.HelpCmd;
import io.github.hotlava03.theogonybot.commands.basic.PingCmd;
import io.github.hotlava03.theogonybot.commands.owner.EvalCmd;
import io.github.hotlava03.theogonybot.commands.util.MythicStatusCmd;

public class Registerer extends CommandRegisterer {
    private TheogonyBot bot;

    public Registerer(TheogonyBot bot) {
        this.bot = bot;
    }

    @Override
    public void handleRegistration() {
        // Basic
        this.registerCommand("help", new HelpCmd(this.bot));
        this.registerCommand("ping", new PingCmd());
        // Util
        this.registerCommand("mythicstatus", new MythicStatusCmd(this.bot));
        // Owner
        this.registerCommand("eval", new EvalCmd(this.bot));

    }
}
