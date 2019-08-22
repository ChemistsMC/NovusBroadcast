package me.ebonjaeger.novusbroadcast.commands

import co.aikar.commands.BaseCommand
import co.aikar.commands.annotation.CommandAlias
import co.aikar.commands.annotation.CommandCompletion
import co.aikar.commands.annotation.CommandPermission
import co.aikar.commands.annotation.Default
import co.aikar.commands.annotation.Description
import co.aikar.commands.annotation.Subcommand
import me.ebonjaeger.novusbroadcast.MessageList
import me.ebonjaeger.novusbroadcast.NovusBroadcast
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

@CommandAlias("novusbroadcast|nb")
class ListCommand(private val plugin: NovusBroadcast) : BaseCommand()
{

    private val PAGE_SIZE = 5

    @Subcommand("list")
    @CommandCompletion("@messageLists")
    @CommandPermission("novusbroadcast.list")
    @Description("Shows all messages in a list.")
    fun onListMessages(sender: CommandSender, listName: String, @Default("1") page: Int)
    {
        val messageList = plugin.messageLists[listName]
        if (messageList == null)
        {
            sender.sendMessage("${ChatColor.RED}» ${ChatColor.GRAY}No list with name '$listName' found!")
            return
        }

        if (messageList.messages.isNullOrEmpty())
        {
            sender.sendMessage("${ChatColor.RED}» ${ChatColor.GRAY}This list has no messages in it!")
            return
        }

        val pages = (messageList.messages.size + PAGE_SIZE - 1) / PAGE_SIZE

        if (page < 1 || page > pages)
        {
            sender.sendMessage("${ChatColor.RED}» ${ChatColor.GRAY}Page '$page' does not exist!")
            return
        }

        val list = buildList(messageList, page, pages)
        sender.sendMessage(list)
    }

    private fun buildList(messageList: MessageList, page: Int, totalPages: Int): String
    {
        val startIndex = (page - 1) * PAGE_SIZE
        var endIndex = startIndex + PAGE_SIZE
        if (endIndex >= messageList.messages.size)
        {
            endIndex = messageList.messages.size - 1
        }

        val sb = StringBuilder()
        sb.append("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH} ---------------${ChatColor.BLUE} Page ${ChatColor.WHITE}$page/$totalPages ${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}--------------- \n")

        for (i in startIndex..endIndex)
        {
            sb.append("${ChatColor.WHITE}$i${ChatColor.GRAY}: ${ChatColor.WHITE}${messageList.messages[i]}\n")
        }

        sb.append("${ChatColor.GRAY}${ChatColor.STRIKETHROUGH} ---------------------------------------------------- \n")

        return sb.toString()
    }
}