package io.sulek.permissionhandlersample

import android.os.Bundle
import androidx.fragment.app.Fragment
import io.sulek.permissionhandler.Scope
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestingScope(Scope.FRAGMENT)
        setContentView(R.layout.activity_fragment)

        firstBtn.setOnClickListener { changeFragment(FirstFragment()) }
        secondBtn.setOnClickListener { changeFragment(SecondFragment()) }
    }

    override fun onDestroy() {
        setRequestingScope(Scope.ACTIVITY)
        super.onDestroy()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentsContainer, fragment).commit()
    }
}
