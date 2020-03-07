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

import net.dv8tion.jda.api.requests.RestAction;
import net.dv8tion.jda.internal.utils.tuple.MutableTriple;
import org.codehaus.groovy.jsr223.GroovyScriptEngineImpl;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import javax.script.ScriptEngine;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class EvalHandler {
    private static final List<String> IMPORTS = Arrays.asList(
            "io.github.hotlava03.theogonybot.commands",
            "io.github.hotlava03.theogonybot.commands.basic",
            "io.github.hotlava03.theogonybot.commands.owner",
            "io.github.hotlava03.theogonybot.commands.util",
            "io.github.hotlava03.theogonybot.commands.fun",
            "io.github.hotlava03.theogonybot.commands.moderation",
            "io.github.hotlava03.theogonybot",
            "io.github.hotlava03.baclavaframework",
            "net.dv8tion.jda.core.entities.impl",
            "net.dv8tion.jda.core.managers",
            "net.dv8tion.jda.core.entities",
            "net.dv8tion.jda.core",
            "net.dv8tion.jda.api",
            "java.lang",
            "java.lang.management",
            "java.util",
            "java.util.stream",
            "java.io",
            "java.math",
            "java.eval",
            "java.eval.concurrent",
            "java.time",
            "java.awt",
            "java.awt.image",
            "javax.imageio",
            "java.time.format"
    );

    private final Scheduler scheduler = Schedulers.newSingle("Eval-Thread");

    public Mono<MutableTriple<?, ?, Boolean>> eval(String toEval, Map<String, Object> shortcuts) {
        final ScriptEngine engine = new GroovyScriptEngineImpl();
        for (Map.Entry<String, Object> shortcut : shortcuts.entrySet())
            engine.put(shortcut.getKey(), shortcut.getValue());

        StringWriter outString = new StringWriter();
        PrintWriter outWriter = new PrintWriter(outString);
        engine.getContext().setWriter(outWriter);
        StringWriter errorString = new StringWriter();
        PrintWriter errorWriter = new PrintWriter(errorString);
        engine.getContext().setErrorWriter(errorWriter);

        final String code = "import " + String.join(".*;\nimport ", IMPORTS) + ".*;\n" + toEval;

        return Mono.fromCallable(() -> {
            Object result;
            try {
                result = engine.eval(code);
            } catch (Throwable e) {
                errorWriter.println(e.getCause().toString());
                return MutableTriple.of(errorString, null, false);
            }

            if (result instanceof RestAction<?>) {
                ((RestAction<?>) result).queue();
                return MutableTriple.of(null, null, false);
            }

            if (result == null) return MutableTriple.of(null, null, false);

            return MutableTriple.of(null, result.toString(), true);
        }).subscribeOn(scheduler);
    }
}
