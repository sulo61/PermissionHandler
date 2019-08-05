package io.sulek.permissionhandler

interface PermissionListener {
    fun onResult(allGranted: Boolean, denied: List<Permission> = listOf())
}