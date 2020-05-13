package presentation.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.droidcon.madrid.R
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_venue.*
import kotlin.math.abs

class VenueFragment : Fragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setup()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_venue, container, false)
    }

    private fun setup() {
        appbar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                collapsing_toolbar.title = getString(R.string.toolbar_venue)
            } else {
                collapsing_toolbar.title = ""
            }
        })

        tv_location.setOnClickListener {
            openMaps()
        }

        tv_maps.setOnClickListener {
            openMaps()
        }

        tv_website.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.venue_website_link)))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        tv_twitter.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.venue_twitter_link)))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        tv_app_description.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun openMaps() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.venue_maps_link)))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }
}