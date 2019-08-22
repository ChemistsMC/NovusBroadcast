package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.MessageList
import me.ebonjaeger.novusbroadcast.NovusBroadcast
import me.ebonjaeger.novusbroadcast.permissions.AdminPermission
import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ListCommand(private val plugin: NovusBroadcast) : ExecutableCommand
{

    private val PAGE_SIZE = 5

    override fun executeCommand(sender: CommandSender?, args: List<String>)
    {
        if (args.size < 2 || args.size > 3)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "Invalid arguments! Usage is: "
                    + ChatColor.WHITE + "/nb list <messageList> [page]")
            return
        }

        val messageList = plugin.messageLists[args[1]]
        if (messageList == null)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "No list with name '${args[1]}' found!")
            return
        }

        val pages = (messageList.messages.size + PAGE_SIZE - 1) / PAGE_SIZE

        val page = getPage(args)
        if (page == null)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "'${args[2]}' is not a number!")
            return
        }

        if (page < 1 || page > pages)
        {
            sender?.sendMessage("" + ChatColor.RED + "» " + ChatColor.GRAY + "Page '$page' does not exist!")
            return
        }

        val list = buildList(messageList, page, pages)
        sender?.sendMessage(list)
    }

    private fun getPage(args: List<String>): Int?
    {
        return if (args.size == 3)
        {
            args[2].toIntOrNull()
        }
        else
        {
            1
        }
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
        sb.append("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + " ---------------" + ChatColor.BLUE + " Page "
                + ChatColor.WHITE + "$page/$totalPages " + ChatColor.GRAY + ChatColor.STRIKETHROUGH + "--------------- \n")

        for (i in startIndex..endIndex)
        {
            sb.append("" + ChatColor.WHITE + "$i" + ChatColor.GRAY + ": " + ChatColor.WHITE + "${messageList.messages[i]}\n")
        }

        sb.append("" + ChatColor.GRAY + ChatColor.STRIKETHROUGH + " ---------------------------------------------------- \n")

        return sb.toString()
    }

    override fun getRequiredPermission(): PermissionNode?
    {
        return AdminPermission.LIST
    }
}