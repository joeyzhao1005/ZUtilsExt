package com.kit.ui.base

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.IdRes
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.kit.utils.intent.ArgumentsManager
import com.kit.utils.log.Zog

/**
 * @author Zhao
 *
 *
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [BaseV4Fragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the newInstance() factory method to
 * create an instance of this fragment.
 */
open class LifecycleKotlinCoroutineFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) = installCoroutineScope(savedInstanceState) {
        super.onCreate(savedInstanceState)
    }


}