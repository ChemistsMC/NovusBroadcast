package me.ebonjaeger.novusbroadcast.permissions

interface PermissionNode
{

    /**
     * Return the node of the permission.
     * For example, 'novusbroadcast.reload'.
     *
     * @return The name of the permission node.
     */
    fun getNode(): String

    /**
     * Return the default permission for this node if no permission server is
     * available.
     *
     * @return The default level of permission.
     */
    fun getDefaultPermission(): DefaultPermission
}