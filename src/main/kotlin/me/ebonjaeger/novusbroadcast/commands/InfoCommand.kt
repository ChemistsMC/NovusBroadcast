package me.ebonjaeger.novusbroadcast.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import me.ebonjaeger.novusbroadcast.NovusBroadcast
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("novusbroadcast|nb")
class InfoCommand(private val plugin: NovusBroadcast) : BaseCommand()
{

    @Subcommand("info")
    @CommandCompletion("@messageLists")
    @CommandPermission("novusbroadcast.info")
    @Description("Shows information about a message list.")
    fun onMessageListInfo(sender: CommandSender, listName: String)
    {
        val messageList = plugin.messageLists[listName]
        if (messageList == null)
        {
            sender.sendMessage("${ChatColor.RED}» ${ChatColor.GRAY}No list with name '$listName' found!")
            return
        }

        var hasPrefix = false
        if (messageList.prefix.isEmpty())
        {
            hasPrefix = true
        }

        var hasSuffix = false
        if (messageList.suffix.isEmpty())
        {
            hasSuffix = true
        }

        val sb = StringBuilder()
        sb.append("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}\n ---------------------------------------------------- \n")
        sb.append("${ChatColor.GRAY}Name: ${ChatColor.WHITE}${messageList.name}\n")
        sb.append("${ChatColor.GRAY}Enabled: ${ChatColor.WHITE}${messageList.enabled}\n")
        sb.append("${ChatColor.GRAY}Interval: ${ChatColor.WHITE}${messageList.interval} seconds\n")
        sb.append("${ChatColor.GRAY}Randomized: ${ChatColor.WHITE}${messageList.randomize}\n")
        sb.append("${ChatColor.GRAY}Has prefix: ${ChatColor.WHITE}$hasPrefix\n")
        sb.append("${ChatColor.GRAY}Has suffix: ${ChatColor.WHITE}$hasSuffix\n")
        sb.append("${ChatColor.GRAY}Message count: ${ChatColor.WHITE}${messageList.messages.size}")
        sb.append("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}\n ---------------------------------------------------- \n")

        sender.sendMessage(sb.toString())
    }
}
