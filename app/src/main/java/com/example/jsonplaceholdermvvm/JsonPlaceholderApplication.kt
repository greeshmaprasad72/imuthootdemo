package com.example.jsonplaceholdermvvm


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// @HiltAndroidApp triggers Hilt's code generation and creates the
// top-level (SingletonComponent) dependency container for the whole app.
@HiltAndroidApp
class JsonPlaceholderApplication : Application()
