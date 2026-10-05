package com.geet.filesearch

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {

    private lateinit var status: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val pad = (24 * resources.displayMetrics.density).toInt()

        val title = TextView(this).apply {
            text = "File Search"
            textSize = 24f
        }

        status = TextView(this).apply {
            text = "Build works. Pick a folder to start."
            textSize = 16f
            setPadding(0, pad, 0, pad)
        }

        val pickButton = Button(this).apply {
            text = "Pick folder"
            setOnClickListener {
                startActivityForResult(Intent(Intent.ACTION_OPEN_DOCUMENT_TREE), PICK_FOLDER)
            }
        }

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(pad, pad * 2, pad, pad)
            addView(title)
            addView(status)
            addView(pickButton)
        }

        setContentView(root)
    }

    @Deprecated("Fine for the scaffold; replaced when the indexer lands")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FOLDER && resultCode == RESULT_OK) {
            val uri = data?.data ?: return
            contentResolver.takePersistableUriPermission(
                uri,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            status.text = "Folder saved:\n$uri"
        }
    }

    companion object {
        private const val PICK_FOLDER = 1
    }
}
