package presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import utils.EXTRA_TAB_TITLE

class TabAdapter(fragmentManager : FragmentManager, private val fragments: List<Fragment>) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

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