package com.hucet.filerebase.appindex

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.google.firebase.appindexing.FirebaseAppIndex
import com.google.firebase.appindexing.FirebaseUserActions
import com.google.firebase.appindexing.Indexable
import com.google.firebase.appindexing.builders.Actions
import com.hucet.filerebase.R

class AppIndexActivity : AppCompatActivity() {

    private val TAG = AppIndexActivity::class.java!!.getName()
    // Define a title for your current page, shown in autocompletion UI
    private val TITLE = "Sample Article"
    private var articleId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_index)

        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent) {
        val action = intent.getAction()
        val data = intent.getData()
        if (Intent.ACTION_VIEW == action && data != null) {
            articleId = data.lastPathSegment
            val linkText = findViewById(R.id.link) as TextView
            linkText.text = data.toString()
        }
    }

    override fun onStart() {
        super.onStart()
        if (articleId != null) {
            val BASE_URL = Uri.parse("https://www.example.com/articles/")
            val APP_URI = BASE_URL.buildUpon().appendPath(articleId).build().toString()

            val articleToIndex = Indexable.Builder()
                    .setName(TITLE)
                    .setUrl(APP_URI)
                    .build()

            val task = FirebaseAppIndex.getInstance().update(articleToIndex)

            // If the Task is already complete, a call to the listener will be immediately
            // scheduled
            task.addOnSuccessListener(this@AppIndexActivity, {
                Log.d(TAG, "App Indexing API: Successfully added $TITLE to index")
            })

            task.addOnFailureListener(this@AppIndexActivity, { exception ->
                Log.e(TAG, "App Indexing API: Failed to add " + TITLE + " to index. " + exception.message)
            })

            // log the view action
            val actionTask = FirebaseUserActions.getInstance().start(Actions.newView(TITLE,
                    APP_URI))

            actionTask.addOnSuccessListener(this@AppIndexActivity, {
                Log.d(TAG, "App Indexing API: Successfully started view action on " + TITLE)
            })

            actionTask.addOnFailureListener(this@AppIndexActivity, { exception ->
                Log.e(TAG, "App Indexing API: Failed to start view action on " + TITLE + ". "
                        + exception.message)
            })
        }
    }

    override fun onStop() {
        super.onStop()

        if (articleId != null) {
            val BASE_URL = Uri.parse("https://www.example.com/articles/")
            val APP_URI = BASE_URL.buildUpon().appendPath(articleId).build().toString()

            val actionTask = FirebaseUserActions.getInstance().end(Actions.newView(TITLE,
                    APP_URI))

            actionTask.addOnSuccessListener(this@AppIndexActivity, { Log.d(TAG, "App Indexing API: Successfully ended view action on " + TITLE) })

            actionTask.addOnFailureListener(this@AppIndexActivity, { exception ->
                Log.e(TAG, "App Indexing API: Failed to end view action on " + TITLE + ". "
                        + exception.message)
            })
        }
    }
}