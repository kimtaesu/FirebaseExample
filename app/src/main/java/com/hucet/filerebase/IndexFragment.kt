package com.hucet.filerebase

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.filerebase.crash.ConfigFragment
import com.hucet.filerebase.crash.CrashFragment
import kotlinx.android.synthetic.main.fragment_index.*

class IndexFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_index, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    fun initViews() {
        crash.setOnClickListener {
            activity.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, CrashFragment())
                    .commit()
        }

        config.setOnClickListener {
            activity.supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container, ConfigFragment())
                    .commit()
        }
    }
}