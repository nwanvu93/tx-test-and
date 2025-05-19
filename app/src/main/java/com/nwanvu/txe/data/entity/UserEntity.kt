package com.nwanvu.txe.data.entity

import com.google.gson.annotations.SerializedName

data class UserEntity(
    val id: Int?,
    val name: String?,
    @SerializedName("login") val username: String?,
    @SerializedName("avatar_url")  val avatar: String?,
    @SerializedName("html_url")  val profileUrl: String? = null,
    val location: String?,
    val bio: String?,
    val blog: String?,
    val followers: Int?,
    val following: Int?,
    /*
    "login": "defunkt",
    "id": 2,
    "node_id": "MDQ6VXNlcjI=",
    "avatar_url": "https://avatars.githubusercontent.com/u/2?v=4",
    "gravatar_id": "",
    "url": "https://api.github.com/users/defunkt",
    "html_url": "https://github.com/defunkt",
    "followers_url": "https://api.github.com/users/defunkt/followers",
    "following_url": "https://api.github.com/users/defunkt/following{/other_user}",
    "gists_url": "https://api.github.com/users/defunkt/gists{/gist_id}",
    "starred_url": "https://api.github.com/users/defunkt/starred{/owner}{/repo}",
    "subscriptions_url": "https://api.github.com/users/defunkt/subscriptions",
    "organizations_url": "https://api.github.com/users/defunkt/orgs",
    "repos_url": "https://api.github.com/users/defunkt/repos",
    "events_url": "https://api.github.com/users/defunkt/events{/privacy}",
    "received_events_url": "https://api.github.com/users/defunkt/received_events",
    "type": "User",
    "user_view_type": "public",
    "site_admin": false
     */

    /*
    {
      "login": "defunkt",
      "id": 2,
      "node_id": "MDQ6VXNlcjI=",
      "avatar_url": "https://avatars.githubusercontent.com/u/2?v=4",
      "gravatar_id": "",
      "url": "https://api.github.com/users/defunkt",
      "html_url": "https://github.com/defunkt",
      "followers_url": "https://api.github.com/users/defunkt/followers",
      "following_url": "https://api.github.com/users/defunkt/following{/other_user}",
      "gists_url": "https://api.github.com/users/defunkt/gists{/gist_id}",
      "starred_url": "https://api.github.com/users/defunkt/starred{/owner}{/repo}",
      "subscriptions_url": "https://api.github.com/users/defunkt/subscriptions",
      "organizations_url": "https://api.github.com/users/defunkt/orgs",
      "repos_url": "https://api.github.com/users/defunkt/repos",
      "events_url": "https://api.github.com/users/defunkt/events{/privacy}",
      "received_events_url": "https://api.github.com/users/defunkt/received_events",
      "type": "User",
      "user_view_type": "public",
      "site_admin": false,
      "name": "Chris Wanstrath",
      "company": null,
      "blog": "http://chriswanstrath.com/",
      "location": null,
      "email": null,
      "hireable": null,
      "bio": "🍔",
      "twitter_username": null,
      "public_repos": 107,
      "public_gists": 274,
      "followers": 22487,
      "following": 215,
      "created_at": "2007-10-20T05:24:19Z",
      "updated_at": "2025-04-14T23:02:42Z"
    }
     */
)
