package com.perqin.copyshare

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_url_selector.*

class UrlSelectorActivity : AppCompatActivity() {
    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_url_selector)

        val urls = intent.getStringArrayListExtra(EXTRA_URLS) ?: emptyList<String>()

        when (urls.size) {
            0 -> finish()
            1 -> {
                viewUrl(urls[0])
                finish()
            }
            else -> {
                behavior = ((bottomSheet.layoutParams as CoordinatorLayout.LayoutParams).behavior as BottomSheetBehavior).apply {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                        override fun onSlide(p0: View, p1: Float) {}

                        override fun onStateChanged(p0: View, state: Int) {
                            if (BottomSheetBehavior.STATE_HIDDEN == state) {
                                finish()
                            }
                        }
                    })
                }
                coordinatorLayout.setOnClickListener {
                    behavior.state = BottomSheetBehavior.STATE_HIDDEN
                }
                recyclerView.apply {
                    adapter = RecyclerAdapter(urls)
                    layoutManager = LinearLayoutManager(this@UrlSelectorActivity)
                }
            }
        }
    }

    private fun viewUrl(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    inner class RecyclerAdapter(private val urls: List<String>) : RecyclerView.Adapter<ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_url_selector, parent, false))
        }

        override fun getItemCount() = urls.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val url = urls[position]
            holder.urlTextView.apply {
                text = url
                setOnClickListener {
                    viewUrl(url)
                    finish()
                }
            }
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val urlTextView: TextView = itemView.findViewById(R.id.urlTextView)
    }

    companion object {
        const val EXTRA_URLS = "urls"
    }
}
