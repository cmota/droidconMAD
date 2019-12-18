package presentation.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.droidcon.madrid.R
import android.support.v4.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import presentation.fragments.FavouritesFragment
import presentation.fragments.SpeakerFragment
import presentation.fragments.TabFragment
import presentation.fragments.VenueFragment
import utils.EXTRA_TAB_SELECTED
import utils.EXTRA_TAB_TITLE
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private var selectedTab = 0
    private val fragments: ArrayList<Fragment> by lazy {
        setup()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val selectedTabId = savedInstanceState?.getInt(EXTRA_TAB_SELECTED) ?: R.id.navigation_schedule

        setupBottomBarActions(selectedTabId)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putInt(EXTRA_TAB_SELECTED, bnv_navigation.selectedItemId)
        super.onSaveInstanceState(outState)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setup(): ArrayList<Fragment> {
        val argSchedule = Bundle()
        argSchedule.putString(EXTRA_TAB_TITLE, getString(R.string.toolbar_schedule))

        val schedule = TabFragment()
        schedule.arguments = argSchedule


        val argFavourites = Bundle()
        argFavourites.putString(EXTRA_TAB_TITLE, getString(R.string.toolbar_favourites))

        val favourites = FavouritesFragment()
        favourites.arguments = argFavourites


        val argSpeakers = Bundle()
        argSpeakers.putString(EXTRA_TAB_TITLE, getString(R.string.toolbar_speakers))

        val speakers = SpeakerFragment()
        speakers.arguments = argSpeakers


        val argVenue = Bundle()
        argVenue.putString(EXTRA_TAB_TITLE, getString(R.string.toolbar_venue))

        val venue = VenueFragment()
        venue.arguments = argVenue

        return arrayListOf(schedule, favourites, speakers, venue)
    }

    private fun setupBottomBarActions(tab: Int) {
        bnv_navigation.setOnNavigationItemSelectedListener { item ->
            val index: Int = when (item.itemId) {
                R.id.navigation_favourites  -> 1
                R.id.navigation_speakers    -> 2
                R.id.navigation_venue       -> 3
                else                        -> 0
            }

            switchFragment(index)
            selectedTab = index

            return@setOnNavigationItemSelectedListener true
        }

        bnv_navigation.selectedItemId = tab
    }

    private fun switchFragment(index: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        val tag = "${fragments[index].arguments?.get(EXTRA_TAB_TITLE)}"

        // if the fragment has not yet been added to the container, add it first
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            transaction.add(R.id.fl_container, fragments[index], tag)

        } else {
            if (fragments[index] === supportFragmentManager.findFragmentByTag(tag)) {
                fragments[index].onResume()

            } else {
                transaction.replace(R.id.fl_container, fragments[index], tag)
            }
        }

        transaction.hide(fragments[selectedTab])
        transaction.show(fragments[index])
        transaction.commit()
    }
}