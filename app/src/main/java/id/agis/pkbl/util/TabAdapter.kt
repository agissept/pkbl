package id.agis.pkbl.util

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var mFragments = ArrayList<Fragment>()
    private var mTitleFragments = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        mFragments.add(fragment)
        mTitleFragments.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleFragments[position]
    }
}