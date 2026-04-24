package com.turkcell.libraryapp.ui.navigation

//magic string ollmasın diye sealed class içinde obje tanımlıyoruz stringleri nachostta kullanmka için
sealed class Screen (val route: String){
    object Login:Screen(route = "login")
    object Register:Screen(route = "register")

}
