package com.hucet.filerebase.crash

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.crash.FirebaseCrash
import com.hucet.filerebase.R
import kotlinx.android.synthetic.main.fragment_crash.*

class CrashFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_crash, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        report_crash.setOnClickListener {
            try {
                throw FirebaseCrashException("Report exception on firebase")
            } catch (e: FirebaseCrashException) {
                FirebaseCrash.report(e)
            }
        }
    }
}