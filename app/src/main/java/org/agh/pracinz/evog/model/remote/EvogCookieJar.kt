package org.agh.pracinz.evog.model.remote

import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


class EvogCookieJar : CookieJar{

    private val cookiesList = mutableListOf<Cookie>()
    override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
        cookiesList.addAll(cookies)
    }

    override fun loadForRequest(url: HttpUrl): MutableList<Cookie> {
        return cookiesList
    }
}