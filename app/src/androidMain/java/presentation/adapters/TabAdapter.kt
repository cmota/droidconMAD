package presentation.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import utils.EXTRA_TAB_TITLE

class TabAdapter(fragmentManager : FragmentManager, private val fragments: List<Fragment>) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "${fragments[position].arguments?.get(EXTRA_TAB_TITLE)}"
    }

    fun getItems(): List<Fragment> {
        return fragments
    }
}