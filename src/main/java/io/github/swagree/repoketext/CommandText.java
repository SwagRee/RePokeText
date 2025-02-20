package io.github.swagree.repoketext;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandText implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender.isOp()){
            if(args.length==0){
                CommandHelper((Player) sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("open")){
                GuiTextInfo.Gui((Player) sender);
                return true;
            }
            if(args[0].equalsIgnoreCase("reload")){
                Main.plugin.reloadConfig();
                sender.sendMessage(ChatColor.BLUE+"重载成功");
                return true;
            }
        }
        return false;
    }
    public void CommandHelper(Player player){
        player.sendMessage("/rpt open 打开测试gui");
        player.sendMessage("/rpt reload 重载插件");
    }
}
