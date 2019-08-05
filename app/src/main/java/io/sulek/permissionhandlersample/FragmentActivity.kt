package io.sulek.permissionhandlersample

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_fragment.*

class FragmentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        firstBtn.setOnClickListener { changeFragment(FirstFragment()) }
        secondBtn.setOnClickListener { changeFragment(SecondFragment()) }
        nextActivity.setOnClickListener { startActivity(Intent(this, SingleActivity::class.java)) }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentsContainer, fragment).commit()
    }
}
