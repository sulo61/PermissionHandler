package io.sulek.permissionhandlersample

import android.Manifest
import android.os.Bundle
import android.util.Log
import io.sulek.permissionhandler.Permission
import io.sulek.permissionhandler.PermissionBundle
import io.sulek.permissionhandler.PermissionListener
import io.sulek.permissionhandler.Scope

class SingleActivity : BaseActivity() {

    private val singleActivityPermissions = mutableListOf(
            Permission(Manifest.permission.CAMERA, Scope.SINGLE_ACTIVITY)
    )
    private val singleActivityScopeRequest = PermissionBundle.ScopeRequest(
            singleActivityPermissions,
            object : PermissionListener {
                override fun onResult(allGranted: Boolean, denied: List<Permission>) {
                    Log.e("PermissionHandler", "singleActivityResult")
                }
            }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        requestSingleActivityPermissions(singleActivityScopeRequest)
    }

    override fun onResume() {
        super.onResume()
        if (getHandlingLifecycleScope() == Scope.SINGLE_ACTIVITY) requestCheckPermissions()
    }

    override fun onDestroy() {
        clearSingleActivityPermission()
        super.onDestroy()
    }
}
