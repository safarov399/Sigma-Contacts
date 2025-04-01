package me.safarov399.highlights

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.highlights.R
import me.safarov399.core.navigation.NavigationManager

class HighlightsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as NavigationManager).toggleMoreVertVisibility(View.GONE)
        return inflater.inflate(R.layout.fragment_highlights, container, false)
    }


}