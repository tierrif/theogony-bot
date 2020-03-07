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
