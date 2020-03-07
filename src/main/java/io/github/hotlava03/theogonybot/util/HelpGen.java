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
