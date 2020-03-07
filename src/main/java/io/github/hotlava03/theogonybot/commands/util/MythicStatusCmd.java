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

package io.github.hotlava03.theogonybot.commands.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import io.github.hotlava03.baclavaframework.Command;
import io.github.hotlava03.baclavaframework.CommandEvent;
import io.github.hotlava03.theogonybot.TheogonyBot;
import io.github.hotlava03.theogonybot.util.Config;
import net.dv8tion.jda.api.EmbedBuilder;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.Instant;

public class MythicStatusCmd extends Command {
    private TheogonyBot bot;

    public MythicStatusCmd(TheogonyBot bot) {
        this.setCategory("util");
        this.bot = bot;
    }

    @Override
    protected String onCommand(CommandEvent e) {
        OkHttpClient client = bot.getHttpClient();
        Request req = new Request.Builder().url("https://api.mcsrvstat.us/2/play.mythicmc.info").build();
        client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException ex) {
                e.reply("Failed to retrieve server info.");
                bot.getFramework().getLogger(this.getClass()).error("Failed to retrieve server info.", ex);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.body() == null) {
                    e.reply("Invalid response body.");
                    return;
                }
                String json = response.body().string();
                Gson gson = new GsonBuilder().create();
                JsonObject obj = gson.fromJson(json, JsonObject.class);
                int online = obj.getAsJsonObject("players").get("online").getAsInt();
                int max = obj.getAsJsonObject("players").get("max").getAsInt();

                EmbedBuilder eb = new EmbedBuilder()
                        .setTitle("Mythic Status")
                        .setColor(Config.MAIN_EMBED_COLOR)
                        .addField("Online count", String.valueOf(online), true)
                        .addField("Max players", String.valueOf(max), true)
                        .setTimestamp(Instant.now());

                e.reply(eb.build());
            }
        });
        return null;
    }
}
