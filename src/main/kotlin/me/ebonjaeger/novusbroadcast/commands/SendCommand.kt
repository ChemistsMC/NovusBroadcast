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
class SendCommand(private val plugin: NovusBroadcast) : BaseCommand()
{

    @Subcommand("send")
    @CommandCompletion("@messageLists")
    @CommandPermission("novusbroadcast.send")
    @Description("Immediately send the message at the given index to all players.")
    fun onSend(sender: CommandSender, listName: String, index: Int)
    {
        val messageList = plugin.messageLists[listName]
        if (messageList == null)
        {
            sender.sendMessage("${ChatColor.RED}» ${ChatColor.GRAY}No list with name '$listName' found!")
            return
        }

        if (messageList.hasMessageAtIndex(index))
        {
            messageList.sendMessage(index)
        } else
        {
            sender.sendMessage("${ChatColor.RED}» ${ChatColor.GRAY}No message at index '$index'!")
        }
    }
}
