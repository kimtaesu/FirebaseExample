package com.hucet.filerebase.crash

import android.os.Bundle
import android.support.annotation.Nullable
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hucet.filerebase.R
import com.hucet.filerebase.config.FirebaseProvider
import kotlinx.android.synthetic.main.fragment_remote_config.*

class ConfigFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_remote_config, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        fetchButton.setOnClickListener {
            welcomeTextView.setText(FirebaseProvider.getString(FirebaseProvider.WELCOME_MESSAGE_KEY))
        }
    }

}