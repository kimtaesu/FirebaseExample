package com.hucet.filerebase.config

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.hucet.filerebase.BuildConfig
import com.hucet.filerebase.R

class FirebaseProvider {
    // Remote Config keys
    companion object {

        lateinit var mFirebaseRemoteConfig: FirebaseRemoteConfig

        init {
            initRemoteConfig()
        }

        const val WELCOME_MESSAGE_KEY = "welcome_message"

        fun getString(key: String): String {
            return mFirebaseRemoteConfig.getString(key)
        }

        private fun initRemoteConfig() {
            // Get Remote Config instance.
            // [START get_remote_config_instance]
            mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
            // Create a Remote Config Setting to enable developer mode, which you can use to increase
            // the number of fetches available per hour during development. See Best Practices in the
            // README for more information.
            // [START enable_dev_mode]
            val configSettings = FirebaseRemoteConfigSettings.Builder()
                    .setDeveloperModeEnabled(BuildConfig.DEBUG)
                    .build()

            mFirebaseRemoteConfig.setConfigSettings(configSettings)
            // [END enable_dev_mode]

            // Set default Remote Config parameter values. An app uses the in-app default values, and
            // when you need to adjust those defaults, you set an updated value for only the values you
            // want to change in the Firebase console. See Best Practices in the README for more
            // information.
            // [START set_default_values]
            mFirebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults)
            fetchConfig()
        }

        fun fetchConfig() {
            var cacheExpiration: Long = 3600 // 1 hour in seconds.
            // If your app is using developer mode, cacheExpiration is set to 0, so each fetch will
            // retrieve values from the service.
            if (mFirebaseRemoteConfig.info.configSettings.isDeveloperModeEnabled) {
                cacheExpiration = 0
            }

            // [START fetch_config_with_callback]
            // cacheExpirationSeconds is set to cacheExpiration here, indicating the next fetch request
            // will use fetch data from the Remote Config service, rather than cached parameter values,
            // if cached parameter values are more than cacheExpiration seconds old.
            // See Best Practices in the README for more information.
            mFirebaseRemoteConfig.fetch(cacheExpiration)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
//                        Toast.makeText(context, "Fetch Succeeded",
//                                Toast.LENGTH_SHORT).show()
                            Log.e(FirebaseProvider::javaClass.name, "Fetch Succeeded")
                            // After config data is successfully fetched, it must be activated before newly fetched
                            // values are returned.
                            mFirebaseRemoteConfig.activateFetched()
                        } else {
//                        Toast.makeText(context, "Fetch Failed",
//                                Toast.LENGTH_SHORT).show()
                            throw RuntimeException("Fetch Failed")
                        }
                    }
                    .addOnFailureListener {
                        // TODO Fail listener
                    }
            // [END fetch_config_with_callback]
        }
    }


}