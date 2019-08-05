package io.sulek.permissionhandlersample

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.sulek.permissionhandler.*

class SecondFragment : Fragment() {

    private var basePermissionActivity: BasePermissionActivity? = null
    private val singleFragmentPermissions = mutableListOf(
            Permission(Manifest.permission.RECORD_AUDIO, Scope.SINGLE_ACTIVITY)
    )
    private val singleFragmentScopeRequest = PermissionBundle.ScopeRequest(
            singleFragmentPermissions,
            object : PermissionListener {
                override fun onResult(allGranted: Boolean, denied: List<Permission>) {
                    Log.e("PermissionHandler", "singleFragmentResult")
                }
            }
    )

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BasePermissionActivity) basePermissionActivity = context
    }

    override fun onDetach() {
        basePermissionActivity?.clearSingleFragmentPermission()
        basePermissionActivity = null
        super.onDetach()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basePermissionActivity?.requestSingleFragmentPermissions(singleFragmentScopeRequest)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onResume() {
        super.onResume()
        basePermissionActivity?.let {
            if (it.getHandlingLifecycleScope() == Scope.ALL_ACTIVITIES) it.requestCheckPermissions()
        }
    }
}
