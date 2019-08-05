package io.sulek.permissionhandler

interface BasePermissionActivity {
    fun requestSingleActivityPermissions(scopeRequest: PermissionBundle.ScopeRequest)
    fun clearSingleActivityPermission()
    fun requestSingleFragmentPermissions(scopeRequest: PermissionBundle.ScopeRequest)
    fun clearSingleFragmentPermission()
    fun getHandlingLifecycleScope(): Scope?
    fun requestCheckPermissions()
}
