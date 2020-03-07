package io.github.hotlava03.theogonybot.commands.basic;

import io.github.hotlava03.baclavaframework.Command;
import io.github.hotlava03.baclavaframework.CommandEvent;

public class PingCmd extends Command {
    public PingCmd() {
        this.setCategory("basic");
    }

    @Override
    protected String onCommand(CommandEvent e) {
        e.getJda().getRestPing().queue(p -> e.reply("Ping: " + p + "ms"), err -> e.reply("Failed to ping."));
        return null;
    }
}
