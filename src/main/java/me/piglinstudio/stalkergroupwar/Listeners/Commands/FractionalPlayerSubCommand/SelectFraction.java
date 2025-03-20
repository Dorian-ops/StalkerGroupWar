package me.piglinstudio.stalkergroupwar.Listeners.Commands.FractionalPlayerSubCommand;

import me.piglinstudio.stalkergroupwar.Utils.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SelectFraction {

    public void execute(Player player) {

        Inventory inv = Bukkit.createInventory(null, 54, "Выбор Фрации: " + player.getName());

        ItemStack fractionFreedom = new ItemStack(Material.TALL_GRASS);
        ItemStack fractionDolg = new ItemStack(Material.CRIMSON_NYLIUM);
        ItemStack fractionMonolit = new ItemStack(Material.DIAMOND);
        ItemStack fractionMilitary = new ItemStack(Material.SHIELD);
        ItemStack fractionBandits = new ItemStack(Material.GUNPOWDER);
        ItemStack fractionSingles = new ItemStack(Material.DEAD_BUSH);

        ItemMeta fractionFreedomItemMeta = fractionFreedom.getItemMeta();
        fractionFreedomItemMeta.setDisplayName(ChatColor.getMsg("§eСвобода"));
        fractionFreedomItemMeta.setLore(Arrays.asList(ChatColor.getMsg("§7Фракция, борющаяся за"),
                "освобождение Зоны от",
                "угнетения и контроля.",
                "Стремятся к независимости и",
                "эксплорации неизведанных территорий."));
        fractionFreedom.setItemMeta(fractionFreedomItemMeta);

        ItemMeta fractionDolgItemMeta = fractionDolg.getItemMeta();
        fractionDolgItemMeta.setDisplayName(ChatColor.getMsg("§9Долг"));
        fractionDolgItemMeta.setLore(Arrays.asList(ChatColor.getMsg("§7Военные и защитники."),
                "Полагаются на дисциплину",
                "и порядок, готовы",
                "защитить Зону от любой",
                "угрозы, считающейся врагом."));
        fractionDolg.setItemMeta(fractionDolgItemMeta);

        ItemMeta fractionMonolitItemMeta = fractionMonolit.getItemMeta();
        fractionMonolitItemMeta.setDisplayName(ChatColor.getMsg("§0Монолит"));
        fractionMonolitItemMeta.setLore(Arrays.asList(ChatColor.getMsg("§7Загадочная и таинственная"),
                "фракция, поклоняющаяся",
                "Монолиту. Идеология",
                "основана на вере и",
                "инстинкте, полагаются на силу."));
        fractionMonolit.setItemMeta(fractionMonolitItemMeta);

        ItemMeta fractionMilitaryItemMeta = fractionMilitary.getItemMeta();
        fractionMilitaryItemMeta.setDisplayName(ChatColor.getMsg("§cВоенные"));
        fractionMilitaryItemMeta.setLore(Arrays.asList(ChatColor.getMsg("§7Военные части, ищущие"),
                "приказов. Обучены",
                "вести бой в любых",
                "условиях и защищать",
                "интересы государства."));
        fractionMilitary.setItemMeta(fractionMilitaryItemMeta);

        ItemMeta fractionBanditsItemMeta = fractionBandits.getItemMeta();
        fractionBanditsItemMeta.setDisplayName(ChatColor.getMsg("§4Бандиты"));
        fractionBanditsItemMeta.setLore(Arrays.asList(ChatColor.getMsg("§7Харизматичные и зловещие"),
                "кочевники, мошенники и",
                "грабители, живущие по",
                "дикому закону. Сила и",
                "разум — их основные союзники."));
        fractionBandits.setItemMeta(fractionBanditsItemMeta);

        ItemMeta fractionSinglesItemMeta = fractionSingles.getItemMeta();
        fractionSinglesItemMeta.setDisplayName(ChatColor.getMsg("§Одиночки"));
        fractionSinglesItemMeta.setLore(Arrays.asList(ChatColor.getMsg("§7Изгои и одиночки, выживающие"),
                "в Зоне за счёт хитрости",
                "и смекалки. Каждый сам за",
                "себя, полагаются на",
                "собственное чутьё в опасной среде."));
        fractionSingles.setItemMeta(fractionSinglesItemMeta);

        inv.setItem(20, fractionBandits);
        inv.setItem(22, fractionDolg);
        inv.setItem(24, fractionFreedom);
        inv.setItem(30, fractionMilitary);
        inv.setItem(32, fractionMonolit);
        inv.setItem(40, fractionSingles);

        player.openInventory(inv);
    }
}
