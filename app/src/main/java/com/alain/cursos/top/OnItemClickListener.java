package com.alain.cursos.top;

/* *
 * Project: MD Top from com.alain.cursos.top
 * Created by Alain Nicolás Tello on 10/11/2019 at 06:36 PM
 * All rights reserved 2020.
 * Course Material Design and Theming Professional for Android
 * More info: https://www.udemy.com/course/material-design-theming-diseno-profesional-para-android/
 * Cursos Android ANT
 */

import android.view.View;

interface OnItemClickListener {
    //void onItemClick(Artista artista);
    //void onItemClick(Artista artista, View view);
    //void onItemClick(Artista artista, View imgPhoto, View tvNote);
    //void onItemClick(Artista artista, View imgPhoto, View tvNote, View tvOrder, View tvName);
    void onItemClick(Artista artista, View imgPhoto, View tvName);
    void onLongItemClick(Artista artista);
}
