package io.github.hotlava03.theogonybot.commands.owner;

import io.github.hotlava03.baclavaframework.Command;
import io.github.hotlava03.baclavaframework.CommandEvent;
import io.github.hotlava03.theogonybot.TheogonyBot;
import io.github.hotlava03.theogonybot.util.EvalHandler;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.InsufficientPermissionException;

import java.util.HashMap;
import java.util.Map;

public class EvalCmd extends Command {
    private final TheogonyBot bot;

    public EvalCmd(TheogonyBot bot) {
        this.setCategory("owner");
        this.setAliases("fuck", "jeval", "ahyescode");
        this.bot = bot;
    }

    @Override
    protected String onCommand(CommandEvent e) {
        EvalHandler eval = bot.getUtilClasses().getEvalHandler();
        Map<String, Object> shortcuts = new HashMap<>();
        shortcuts.put("e", e);
        shortcuts.put("event", e);
        shortcuts.put("jda", e.getJda());
        shortcuts.put("channel", e.getChannel());
        if (e.hasGuild()) {
            shortcuts.put("guild", e.getGuild());
            shortcuts.put("guildId", e.getGuild().getId());
            shortcuts.put("member", e.getMember());
        }
        shortcuts.put("author", e.getAuthor());
        shortcuts.put("message", e.getMessage());
        shortcuts.put("bot", bot);
        shortcuts.put("client", bot.getFramework());
        shortcuts.put("framework", bot.getFramework());
        shortcuts.put("registerer", bot.getFramework().getCommandRegisterer());

        eval.eval(String.join(" ", e.getArgs()), shortcuts).subscribe(res -> {
            final Object error = res.getLeft();
            final Object success = res.getMiddle();
            final boolean hasResult = res.getRight();

            String toSend;
            if (error != null) toSend = error(e.getMessage(), error);
            else toSend = success(e.getMessage(), success, hasResult);

            if (toSend != null) e.reply(toSend);
        });

        return null;
    }

    private String error(Message message, Object error) {
        String toReturn = null;

        try {
            message.addReaction("\u274C").queue();
        } catch (InsufficientPermissionException e) {
            toReturn = "\u274C | Check your DMs.";
        }

        boolean hasPerm = toReturn == null;
        message.getAuthor().openPrivateChannel().queue(c -> {
            if (hasPerm) handleLongMessage(c, error.toString());
            else handleLongMessage(c, "No add reaction permissions.\n" + error);
        });

        return toReturn;
    }

    private void handleLongMessage(MessageChannel channel, String longMessage) {
        if (longMessage.length() < 2000) {
            channel.sendMessage(longMessage).queue();
            return;
        }
        String toSend = longMessage.trim();
        while (toSend.length() > 2000) {
            int index = toSend.lastIndexOf("\n\n", 2000);
            if (index == -1)
                index = toSend.lastIndexOf("\n", 2000);
            if (index == -1)
                index = toSend.lastIndexOf(" ", 2000);
            if (index == -1)
                index = 2000;
            channel.sendMessage(toSend.substring(0, index)).queue();
            toSend = toSend.substring(index).trim();
        }
    }

    private String success(Message message, Object success, boolean hasResult) {
        try {
            message.addReaction("\u2705").queue();
        } catch (InsufficientPermissionException ignore) {}

        if (hasResult) return success.toString();
        else return null;
    }
}
