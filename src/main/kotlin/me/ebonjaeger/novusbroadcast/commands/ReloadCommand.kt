package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.NovusBroadcast
import me.ebonjaeger.novusbroadcast.permissions.AdminPermission
import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class ReloadCommand(private val plugin: NovusBroadcast): ExecutableCommand
{

    override fun executeCommand(sender: CommandSender?, args: List<String>?)
    {
        plugin.reload()

        sender?.sendMessage("" + ChatColor.BLUE + "» " + ChatColor.GRAY + "Configuration files reloaded!")
    }

    override fun getRequiredPermission(): PermissionNode?
    {
        return AdminPermission.RELOAD
    }
}
