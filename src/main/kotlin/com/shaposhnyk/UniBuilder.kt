package com.shaposhnyk

import java.io.Serializable
import java.net.URI
import java.net.URLEncoder

/**
 * Universal UriBuilder.
 * Immutable and Thread-safe. Returns new instance every time
 */
data class UniBuilder(val base: String, val path: String, val params: List<Pair<String, String>>) : Serializable {

    companion object Factory {
        fun of(uriString: String): UniBuilder {
            return of(URI(uriString))
        }

        fun of(uri: URI): UniBuilder {
            val base = (uri?.scheme + ":") + "//" + uri.authority
            return UniBuilder(base, uri.path, queryList(uri.query));
        }

        private fun queryList(queryString: String?): List<Pair<String, String>> = queryString
                ?.split("&")
                ?.map { pv -> Pair(pv.substring(0, pv.indexOf("=")), pv.substring(pv.indexOf("=") + 1)) }
                ?: emptyList()
    }

    fun build(): URI {
        if (params.isEmpty()) {
            return URI(base + path);
        }

        return URI(base + path + (if (path.contains("?")) "&" else "?") + getQueryString())
    }

    fun getQueryString(): String {
        return params.map { p -> escapeQuery(p.first) + "=" + escapeQuery(p.second) }
                .joinToString("&")
    }

    private fun escapeQuery(string: String): String {
        return URLEncoder.encode(string, "UTF-8")
    }

    /**
     * @return new UniBuilder with added given query parameter
     */
    fun param(param: String, value: String): UniBuilder {
        val newList = params.toMutableList().plus(Pair(param, value))
        return UniBuilder(base, path, newList)
    }

    /**
     * @return new UniBuilder with added given query parameters
     */
    fun params(paramMap: Map<String, String>): UniBuilder {
        val newParams = params.flatMap { paramMap.map { e -> Pair(e.key, e.value) } }
        return UniBuilder(base, path, newParams)
    }

    /**
     * @param newPath - path to substitute must either empty or starting with a slash
     * @return builder with new path
     * @throws IllegalArgumentException if new path is not empty or strarting with a slash
     */
    fun path(newPath: String): UniBuilder {
        if (!("".equals(newPath) || newPath.startsWith("/"))) {
            throw IllegalArgumentException("path either must be empty or start with a slash: " + newPath)
        }
        return UniBuilder(base, newPath, params)
    }

    /**
     * @param suffix - path suffix to append if current path is empty it must be starting with a slash
     * @return builder with new path
     * @throws IllegalArgumentException if new path is not empty or strarting with a slash
     */
    fun appendPath(suffix: String): UniBuilder {
        return path(path + suffix)
    }

    fun dropParams(): UniBuilder {
        return UniBuilder(base, path, emptyList())
    }

    fun dropParams(param: String): UniBuilder {
        return UniBuilder(base, path, params.filter { pv -> pv.first.equals(param) })
    }

    override fun toString(): String {
        return "B${base}P${path}Q${params}"
    }

}