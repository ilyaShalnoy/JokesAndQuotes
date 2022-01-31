package com.example.notes.jokeapp.domain

import java.lang.Exception

class NoConnectionException : Exception()
class ServiceUnavailableException : Exception()
class NoCachedJokesException : Exception()