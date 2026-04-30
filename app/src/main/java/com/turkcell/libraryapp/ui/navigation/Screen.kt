package com.turkcell.libraryapp.ui.navigation

sealed class Screen (val route: String)
{
    object Login: Screen(route = "login")
    object Register: Screen(route = "register")

    object Homepage: Screen(route = "homepage")

}