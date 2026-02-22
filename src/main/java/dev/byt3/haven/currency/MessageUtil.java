package dev.byt3.haven.currency;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;

import javax.annotation.Nullable;
import java.awt.*;

public class MessageUtil {
    public static void success(CommandContext ctx, String message) {
        message(ctx, message, Color.green);
    }

    public static void error(CommandContext ctx, String message) {
        message(ctx, message, Color.red);
    }

    public static void message(CommandContext ctx, String message, @Nullable Color color) {
        ctx.sendMessage(Message.raw(message).color(color != null ? color : Color.white));
    }
}
