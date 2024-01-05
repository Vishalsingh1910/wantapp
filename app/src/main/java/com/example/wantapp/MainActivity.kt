package com.example.wantapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.wantapp.R.id.TabLayout_main
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar         : androidx.appcompat.widget.Toolbar
    private lateinit var tabLayout       : TabLayout
    private lateinit var viewPager2      : ViewPager2
    private lateinit var appPagerAdapter : AppPagerAdapter
    private val titles = arrayListOf("chats","status","calls")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar_main)
        tabLayout = findViewById(R.id.TabLayout_main)
        viewPager2 = findViewById(R.id.viewpager2)
        toolbar.title = "wantapp"
        setSupportActionBar(toolbar)
        appPagerAdapter = AppPagerAdapter(this)
        viewPager2.adapter = appPagerAdapter
        TabLayoutMediator(tabLayout,viewPager2){
            tab,position->
            tab.text = titles[position]
        }.attach()
    }
    class AppPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> chats()
                1 -> status()
                2 -> calls()

                else -> chats()
            }
        }
    }
}