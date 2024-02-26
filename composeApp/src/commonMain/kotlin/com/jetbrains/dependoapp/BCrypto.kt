package com.jetbrains.dependoapp

expect suspend fun genrateHasPassword(password:String):String
expect suspend fun genrateXsignId(mobile:String,randomId:String):String
expect suspend fun uUIDGenerator():String
expect suspend fun generateSha512(input:String):String