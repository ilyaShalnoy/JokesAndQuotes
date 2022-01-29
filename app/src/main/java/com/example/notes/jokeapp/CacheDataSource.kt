package com.example.notes.jokeapp

interface CacheDataSource: JokeDataFetcher<Joke, Unit>, ChangeJokeStatus {
}