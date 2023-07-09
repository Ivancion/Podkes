package com.example.podcastapp.data.exceptions

import java.lang.Exception

class ServerException(val msg: String): Exception(msg)

class CacheException(val msg: String): Exception(msg)
