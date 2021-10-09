package com.kit.ui.base

import androidx.viewbinding.ViewBinding
import com.kit.ui.base.BaseAppCompatActivity
import com.kit.ui.base.BaseFragment.OnFragmentInteractionListener
import android.os.Bundle
import android.content.Intent
import android.util.AndroidRuntimeException
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.kit.utils.log.Zog

/**
 * @author joeyzhao
 */
abstract class BaseActivity<VB : ViewBinding?> : BaseAppCompatActivity<VB>(),
    OnFragmentInteractionListener {


    protected val FragmentActivity.decorView: ViewGroup?
        get() = window?.decorView as ViewGroup?

    protected val FragmentActivity.contentView: ViewGroup?
        get() = decorView?.findViewById(android.R.id.content)

    protected val FragmentActivity.rootView: View?
        get() = contentView?.getChildAt(0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun startActivity(intent: Intent) {
        try {
            super.startActivity(intent)
        } catch (e: AndroidRuntimeException) {
            Zog.showException(e)
        }
    }
}