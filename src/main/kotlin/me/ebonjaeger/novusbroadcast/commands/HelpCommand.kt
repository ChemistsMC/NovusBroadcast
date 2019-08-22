package me.ebonjaeger.novusbroadcast.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.CommandHelp
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.HelpCommand
import co.aikar.commands.annotation.Subcommand
import me.ebonjaeger.novusbroadcast.NovusBroadcast
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("novusbroadcast|nb")
class HelpCommand(private val plugin: NovusBroadcast) : BaseCommand()
{

    @HelpCommand
    fun onHelp(sender: CommandSender, help: CommandHelp)
    {
        sender.sendMessage("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}-----------------------------------------------------")
        sender.sendMessage("${ChatColor.GRAY}                [ ${ChatColor.BLUE}NovusBroadcast Commands${ChatColor.GRAY} ]\n\n")
        help.showHelp()
        sender.sendMessage("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}-----------------------------------------------------")
    }

    @Subcommand("version")
    @CommandPermission("novusbroadcast.version")
    @Description("View the installed version of NovusBroadcast")
    fun onVersion(sender: CommandSender)
    {
        val version = plugin.description.version
        val authors = plugin.description.authors
                .toString()
                .replace("[", "")
                .replace("]", "")

        sender.sendMessage("${ChatColor.BLUE}» ${ChatColor.GRAY}Version: ${ChatColor.BLUE}$version")
        sender.sendMessage("${ChatColor.BLUE}» ${ChatColor.GRAY}Authors: ${ChatColor.BLUE}$authors")
    }
}