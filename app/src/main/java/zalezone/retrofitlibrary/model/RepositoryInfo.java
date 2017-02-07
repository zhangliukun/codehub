package zalezone.retrofitlibrary.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zale on 2017/1/23.
 */

public class RepositoryInfo extends GithubBaseModel{

    private long id;
    private String name;
    @SerializedName("full_name")
    private String fullName;
    private OwnerModel owner;
    @SerializedName("private")
    private boolean isPrivate;
    private String url;
    private String description;
    private boolean fork;
    @SerializedName("stargazers_count")
    private int starsCount;
    @SerializedName("watchers_count")
    private int watchersCount;
    @SerializedName("open_issues")
    private int openIssues;
    private String language;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;

//    {
//        "id":18905701,
//            "name":"FPP_JAVA",
//            "full_name":"SKLCC/FPP_JAVA",
//            "owner":{
//                "login":"SKLCC",
//                "id":7334291,
//                "avatar_url":"https://avatars.githubusercontent.com/u/7334291?v=3",
//                "gravatar_id":"",
//                "url":"https://api.github.com/users/SKLCC",
//                "html_url":"https://github.com/SKLCC",
//                "followers_url":"https://api.github.com/users/SKLCC/followers",
//                "following_url":"https://api.github.com/users/SKLCC/following{/other_user}",
//                "gists_url":"https://api.github.com/users/SKLCC/gists{/gist_id}",
//                "starred_url":"https://api.github.com/users/SKLCC/starred{/owner}{/repo}",
//                "subscriptions_url":"https://api.github.com/users/SKLCC/subscriptions",
//                "organizations_url":"https://api.github.com/users/SKLCC/orgs",
//                "repos_url":"https://api.github.com/users/SKLCC/repos",
//                "events_url":"https://api.github.com/users/SKLCC/events{/privacy}",
//                "received_events_url":"https://api.github.com/users/SKLCC/received_events",
//                "type":"Organization",
//                "site_admin":false
//            },
//        "private":false,
//        "html_url":"https://github.com/SKLCC/FPP_JAVA",
//        "description":"A Server",
//        "fork":false,
//        "url":"https://api.github.com/repos/SKLCC/FPP_JAVA",
//        "forks_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/forks",
//        "keys_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/keys{/key_id}",
//        "collaborators_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/collaborators{/collaborator}",
//        "teams_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/teams",
//        "hooks_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/hooks",
//        "issue_events_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/issues/events{/number}",
//        "events_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/events",
//        "assignees_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/assignees{/user}",
//        "branches_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/branches{/branch}",
//        "tags_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/tags",
//        "blobs_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/git/blobs{/sha}",
//        "git_tags_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/git/tags{/sha}",
//        "git_refs_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/git/refs{/sha}",
//        "trees_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/git/trees{/sha}",
//        "statuses_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/statuses/{sha}",
//        "languages_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/languages",
//        "stargazers_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/stargazers",
//        "contributors_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/contributors",
//        "subscribers_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/subscribers",
//        "subscription_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/subscription",
//        "commits_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/commits{/sha}",
//        "git_commits_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/git/commits{/sha}",
//        "comments_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/comments{/number}",
//        "issue_comment_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/issues/comments{/number}",
//        "contents_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/contents/{+path}",
//        "compare_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/compare/{base}...{head}",
//        "merges_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/merges",
//        "archive_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/{archive_format}{/ref}",
//        "downloads_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/downloads",
//        "issues_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/issues{/number}",
//        "pulls_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/pulls{/number}",
//        "milestones_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/milestones{/number}",
//        "notifications_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/notifications{?since,all,participating}",
//        "labels_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/labels{/name}",
//        "releases_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/releases{/id}",
//        "deployments_url":"https://api.github.com/repos/SKLCC/FPP_JAVA/deployments",
//        "created_at":"2014-04-18T07:08:31Z",
//        "updated_at":"2016-04-12T02:27:12Z",
//        "pushed_at":"2014-08-19T06:30:04Z",
//        "git_url":"git://github.com/SKLCC/FPP_JAVA.git",
//        "ssh_url":"git@github.com:SKLCC/FPP_JAVA.git",
//        "clone_url":"https://github.com/SKLCC/FPP_JAVA.git",
//        "svn_url":"https://github.com/SKLCC/FPP_JAVA",
//        "homepage":"",
//        "size":484,
//        "stargazers_count":1,
//        "watchers_count":1,
//        "language":"Java",
//        "has_issues":true,
//        "has_downloads":true,
//        "has_wiki":true,
//        "has_pages":false,
//        "forks_count":0,
//        "mirror_url":null,
//        "open_issues_count":0,
//        "forks":0,
//        "open_issues":0,
//        "watchers":1,
//        "default_branch":"master",
//        "permissions":{
//            "admin":true,
//            "push":true,
//            "pull":true
//        }
//    }


}
