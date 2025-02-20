package io.github.swagree.repoketext;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.arasple.mc.trmenu.taboolib.module.nms.NMSItemTagKt;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class Util {
    public static void toJson(Player player, ItemStack itemStack) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", itemStack.getType().name());
        MaterialData materialData = itemStack.getData();
        jsonObject.addProperty("data", (Number) materialData.getData());
        jsonObject.addProperty("amount", (Number) itemStack.getAmount());

        jsonObject.add("meta", new Gson().toJsonTree((Object) NMSItemTagKt.getItemTag(itemStack)));
        String string2 = jsonObject.toString();

        TextComponent textSure = new TextComponent(ChatColor.AQUA + "[点击复制]");
        textSure.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, string2));
        System.out.println(string2);
        Main.plugin.getConfig().set("TextJson",string2);
        player.spigot().sendMessage(new BaseComponent[]{textSure});
    }

    public static Integer getIntegerConfig(String path) { return Main.plugin.getConfig().getInt(path);}
    public static Double getDoubleConfig(String path) {
        return Main.plugin.getConfig().getDouble(path);
    }

    public static String getStringConfig(String path) {
        return Main.plugin.getConfig().getString(path);
    }
}
