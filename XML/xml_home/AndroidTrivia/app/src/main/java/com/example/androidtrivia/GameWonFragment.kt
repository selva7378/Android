/*
 * Copyright 2018, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.androidtrivia


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.androidtrivia.databinding.FragmentGameWonBinding


class GameWonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentGameWonBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_game_won, container, false
        )
        binding.nextMatchButton.setOnClickListener { view: View ->
            findNavController().navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
        }

        return binding.root
    }

    // Inside your Fragment class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get the MenuHost
        val menuHost: MenuHost = requireActivity()

        // Add a menu provider to the MenuHost
        menuHost.addMenuProvider(object : MenuProvider {
            // This is where you inflate the menu
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.winner_menu, menu)
            }

            // This is where you handle menu item clicks
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.share -> {
                        // Call your existing shareSuccess() method
                        shareSuccess()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun getShareIntent(): Intent {
        val args = GameWonFragmentArgs.fromBundle(requireArguments())
        val shareText =
            getString(R.string.share_success_text, args.numCrctQuestions, args.totalQuestions)

        return ShareCompat.IntentBuilder(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
//        return Intent(Intent.ACTION_SEND)
//            .setType("text/plain")
//            .putExtra(
//                Intent.EXTRA_TEXT,
//                getString(R.string.share_success_text, args.numCrctQuestions, args.totalQuestions)
//            )
    }

    private fun shareSuccess() {
        startActivity(getShareIntent())
    }
}
