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
