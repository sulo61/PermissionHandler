package io.sulek.permissionhandlersample

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import io.sulek.permissionhandler.Permission
import io.sulek.permissionhandler.PermissionBundle
import io.sulek.permissionhandler.PermissionListener
import io.sulek.permissionhandler.Scope
import kotlinx.android.synthetic.main.activity_single.*

class SingleActivity : BaseActivity() {

    private val singleActivityPermissions = mutableListOf(
            Permission(Manifest.permission.CAMERA, Scope.ACTIVITY)
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
        goToFragmentsBtn.setOnClickListener { startActivity(Intent(this, FragmentActivity::class.java)) }
    }

    override fun onDestroy() {
        clearSingleActivityPermission()
        super.onDestroy()
    }
}
