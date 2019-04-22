package uk.co.massimocarli.recyclerviewtest.ui.navigation

import androidx.fragment.app.Fragment

/**
 * Simple Navigation for Fragments into an Activity
 */
interface Navigation {

  /**
   * Navigate to the given fragment with the given tag. It allows you to add the Fragment to
   * the backstack
   */
  fun replaceFragment(
    fragment: Fragment,
    backStackName: String? = null,
    tag: String? = null
  )

  /**
   * Back in the navigation stack
   */
  fun back()
}