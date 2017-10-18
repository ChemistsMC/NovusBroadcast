package me.ebonjaeger.novusbroadcast.commands

import me.ebonjaeger.novusbroadcast.permissions.PermissionNode
import org.bukkit.command.CommandSender

interface ExecutableCommand
{

    /**
     * Executes the command with the given arguments.
     *
     * @param sender The person performing the command.
     * @param args The arguments given by the sender.
     */
    fun executeCommand(sender: CommandSender?, args:List<String>?)

    /**
     * Returns the permission required to execute this command, or null if it is not restricted.
     *
     * @return The required permission node, or null
     */
    fun getRequiredPermission(): PermissionNode?
    {
        return null
    }
}