package io.github.swagree.repoketext;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

import java.util.UUID;

public class TextGuiListener implements Listener {
    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        InventoryHolder holder = e.getInventory().getHolder();
        if(!(holder instanceof GuiTextInfo.MyHolder)){
            return;
        }
    }


}


