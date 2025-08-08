package com.example.git_api_recr.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchGitHubDto {
    private String name;
    private Commit commit;

    public String getName() { return name; }

    public Commit getCommit() { return commit; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Commit {
        private String sha;

        public String getSha() { return sha; }
    }
}