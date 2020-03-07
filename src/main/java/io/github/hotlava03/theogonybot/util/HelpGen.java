package io.github.hotlava03.theogonybot.util;

import io.github.hotlava03.baclavaframework.Command;
import io.github.hotlava03.theogonybot.TheogonyBot;

import java.util.Map;

public class HelpGen {
    private TheogonyBot bot;

    public HelpGen(TheogonyBot bot) {
        this.bot = bot;
    }

    public String generateHelp() {
        StringBuilder sb = new StringBuilder().append("**TheogonyBot help**\n\n");
        for (Map.Entry<String, Command> entry : bot.getFramework().getCommandRegisterer().getCommands().entrySet())
            sb.append("**").append(bot.getFramework().getPrefix())
                    .append(entry.getKey()).append("**").append(" - ")
                    .append(entry.getValue().getUsage()).append("\n");
        return sb.toString();
    }
}
