package me.ebonjaeger.novusbroadcast.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import me.ebonjaeger.novusbroadcast.NovusBroadcast
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("novusbroadcast|nb")
class ReloadCommand(private val plugin: NovusBroadcast) : BaseCommand()
{

    @Subcommand("reload")
    @CommandPermission("novusbroadcast.reload")
    @Description("Reloads the plugin config and message lists.")
    fun onReload(sender: CommandSender)
    {
        plugin.reload()
        sender.sendMessage("${ChatColor.BLUE}» ${ChatColor.GRAY}Configuration files reloaded!")
    }
}
