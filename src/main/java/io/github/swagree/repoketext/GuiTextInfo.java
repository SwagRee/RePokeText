package io.github.swagree.repoketext;

import com.aystudio.core.pixelmon.PokemonAPI;
import com.pixelmonmod.pixelmon.api.pokemon.Pokemon;
import com.pixelmonmod.pixelmon.api.pokemon.PokemonSpec;
import com.pixelmonmod.pixelmon.items.ItemUIElement;
import me.badbones69.crazycrates.Methods;
import me.badbones69.crazycrates.api.objects.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class GuiTextInfo {
    public static void Gui(Player player) {
        MyHolder myHolder = new MyHolder(player);
        player.openInventory(myHolder.getInventory());
    }

    public static class MyHolder implements InventoryHolder {
        public Inventory inv;
        public Player player;
        public MyHolder(Player player){
            this.player= player;
            this.inv = Bukkit.createInventory(this, 54, ChatColor.AQUA+"Test");

        }
        @Override
        public Inventory getInventory() {
            Integer sizeWeight = Util.getIntegerConfig("TextInfo.Size.weight");
            Integer sizeHeight = Util.getIntegerConfig("TextInfo.Size.height");
            Integer ColorR = Util.getIntegerConfig("TextInfo.Color.Red");
            Integer ColorG = Util.getIntegerConfig("TextInfo.Color.Green");
            Integer ColorB = Util.getIntegerConfig("TextInfo.Color.Blue");
            Integer ColorA = Util.getIntegerConfig("TextInfo.Color.Alpha");
            String stringConfig = Util.getStringConfig("TextInfo.text").replace("&", "§");
            Double TextureSizeWeight = Util.getDoubleConfig("TextInfo.TextureSize.weight");
            Double TextureSizeHeight = Util.getDoubleConfig("TextInfo.TextureSize.height");
            Double textScale = Util.getDoubleConfig("TextInfo.TextScale");
            Integer PosOffsetX = Util.getIntegerConfig("TextInfo.PosOffset.weight");
            Integer PosOffsetY = Util.getIntegerConfig("TextInfo.PosOffset.height");
            String image = Util.getStringConfig("TextInfo.imagePath");
            Integer slot = Util.getIntegerConfig("TextInfo.slot");

            ItemStack textInfoItemStack = getTextInfo(image, sizeWeight, sizeHeight, ColorR, ColorG, ColorB, ColorA, stringConfig, TextureSizeWeight, TextureSizeHeight, textScale, PosOffsetX, PosOffsetY);

            inv.setItem(slot, textInfoItemStack);
            Util.toJson(player, textInfoItemStack);

            player.openInventory(inv);
            return inv;
        }
    }
    public static ItemStack getTextInfo(String image,
                                        Integer sizeWeight, Integer sizeHeight,
                                        Integer colorR, Integer colorG, Integer colorB, Integer colorA,
                                        String text, Double TextureSizeWeight, Double TextureSizeHeight,
                                        Double TextScale,
                                        Integer PosOffsetX, Integer PosOffsetY
    ) {
        net.minecraft.item.ItemStack itemStack = ItemUIElement.builder()
                .setImage(image)
                .setSize(sizeWeight, sizeHeight)
                .setColor(colorR, colorG, colorB, colorA)
                .setText(text).setTextureSize(Float.valueOf(String.valueOf(TextureSizeWeight)), Float.valueOf(String.valueOf(TextureSizeHeight)))
                .setTextScale(Float.valueOf(String.valueOf(TextScale)))
                .setPosOffset(PosOffsetX, PosOffsetY)
                .build();
        org.bukkit.inventory.ItemStack pokeItem = CraftItemStack.asBukkitCopy((net.minecraft.server.v1_12_R1.ItemStack) (Object) itemStack);
        ItemMeta itemMeta = pokeItem.getItemMeta();
        itemMeta.setDisplayName(text);
        pokeItem.setItemMeta(itemMeta);
        return pokeItem;
    }

    private ItemBuilder getDisplayItem(FileConfiguration file, String prize) {
        String path = "Crate.Prizes." + prize + ".";
        ItemBuilder itemBuilder = new ItemBuilder();
        String displayItemKey = file.getString(path + "DisplayItem");

        try {
            if (displayItemKey.startsWith("PIXELMON_SPRITE:")) {
                String withoutPrefix = displayItemKey.substring("PIXELMON_SPRITE:".length());
                String[] parts = withoutPrefix.split(":", 2);
                String pokemonName = parts[0];
                String formName = null;
                if (parts.length > 1) {
                    formName = parts[1];
                }

                if (formName != null) {
                    Pokemon pokemon = PokemonSpec.from(pokemonName).create();
                    pokemon.setForm(Integer.parseInt(formName));
                    PokemonAPI pokemonAPI = new PokemonAPI();
                    pokemonAPI.getSpriteHelper().getSpriteItem(pokemon);
                    itemBuilder.setReferenceItem(pokemonAPI.getSpriteHelper().getSpriteItem(pokemon));
                } else {
                    PokemonAPI pokemonAPI = new PokemonAPI();
                    itemBuilder.setReferenceItem(pokemonAPI.getSpriteHelper().getSpriteItem(PokemonSpec.from(pokemonName).create()));
                }
            }

            itemBuilder.setMaterial(file.getString(path + "DisplayItem")).setAmount(file.getInt(path + "DisplayAmount", 1)).setName(file.getString(path + "DisplayName")).setLore(file.getStringList(path + "Lore")).setGlowing(file.getBoolean(path + "Glowing")).setUnbreakable(file.getBoolean(path + "Unbreakable")).hideItemFlags(file.getBoolean(path + "HideItemFlags")).addItemFlags(file.getStringList(path + "Flags")).addPatterns(file.getStringList(path + "Patterns")).setPlayer(file.getString(path + "Player"));
            if (file.contains(path + "DisplayEnchantments")) {
                for(String enchantmentName : file.getStringList(path + "DisplayEnchantments")) {
                    Enchantment enchantment = Methods.getEnchantment(enchantmentName.split(":")[0]);
                    if (enchantment != null) {
                        itemBuilder.addEnchantments(enchantment, Integer.parseInt(enchantmentName.split(":")[1]));
                    }
                }
            }

            return itemBuilder;
        } catch (Exception var11) {
            return (new ItemBuilder()).setMaterial("RED_TERRACOTTA", "STAINED_CLAY:14").setName("&c&lERROR").setLore(Arrays.asList("&cThere is an error", "&cFor the reward: &c" + prize));
        }
    }


}
