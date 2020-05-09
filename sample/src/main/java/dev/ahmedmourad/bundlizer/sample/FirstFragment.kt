package dev.ahmedmourad.bundlizer.sample

import androidx.fragment.app.Fragment

class FirstFragment : Fragment(R.layout.fragment_first) {
    override fun onStart() {
        super.onStart()
        SecondFragmentArgs
    }
}
