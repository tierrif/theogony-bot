/*
 * TheogonyBot - Simple utility Discord bot that uses the BaclavaFramework.
 * Copyright (C) 2020 HotLava03
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package io.github.hotlava03.theogonybot.commands.basic;

import io.github.hotlava03.baclavaframework.Command;
import io.github.hotlava03.baclavaframework.CommandEvent;
import io.github.hotlava03.theogonybot.TheogonyBot;
import io.github.hotlava03.theogonybot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.time.Instant;
import java.util.Arrays;
import java.util.Map;

public class HelpCmd extends Command {
    private final TheogonyBot bot;

    public HelpCmd(TheogonyBot bot) {
        this.setCategory("basic");
        this.setUsage("/help [command]");
        this.setAliases("halp", "canyouhelpme", "?");
        this.setExamples("/help ping\n/help mythicstatus");
        this.bot = bot;
    }

    @Override
    protected String onCommand(CommandEvent e) {
        if (e.getArgs().length == 0) {
            e.reply(fullHelpEmbed());
            return null;
        }

        Command command = bot.getFramework().getCommandRegisterer().getCommandByName(e.getArgs()[0]);
        if (command == null) return "Command not found.";

        String usage = command.getUsage() == null ?
                bot.getFramework().getPrefix() + e.getArgs()[0] : command.getUsage();
        String aliases = command.getAliases().length == 0 ?
                "No aliases." : String.join(", ", Arrays.asList(command.getAliases()));
        String examples = command.getExamples() == null ?
                "No examples." : command.getExamples();

        e.reply(usageEmbed(e.getArgs()[0], usage, aliases, examples));
        return null;
    }

    private MessageEmbed fullHelpEmbed() {
        EmbedBuilder eb = new EmbedBuilder()
                .setTitle("TheogonyBot help")
                .setColor(Config.MAIN_EMBED_COLOR);

        Map<String, Command> commands = bot.getFramework().getCommandRegisterer().getCommands();
        for (Map.Entry<String, Command> command : commands.entrySet())
            eb.addField(command.getKey(), command.getValue().getUsage() == null ?
                    bot.getFramework().getPrefix() + command.getKey() :
                    command.getValue().getUsage(), true);

        eb.setTimestamp(Instant.now())
                .setFooter("Use " + bot.getFramework().getPrefix() + "help <command> for detailed help.");
        return eb.build();
    }

    private MessageEmbed usageEmbed(String cmd, String usage, String aliases, String examples) {
        return new EmbedBuilder()
                .setTitle("Usage for " + cmd)
                .setColor(Config.MAIN_EMBED_COLOR)
                .addField("Syntax", usage, false)
                .addField("Aliases", aliases, false)
                .addField("Examples", examples, false)
                .setTimestamp(Instant.now())
                .setFooter("Use " + bot.getFramework().getPrefix() + "help for a list of commands.")
                .build();
    }
}
