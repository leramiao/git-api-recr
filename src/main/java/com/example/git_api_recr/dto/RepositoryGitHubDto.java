package com.example.git_api_recr.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RepositoryGitHubDto {
    private String name;
    private Owner owner;
    private boolean fork;

    public String getName() { return name; }

    public Owner getOwner() { return owner; }

    public boolean isFork() { return fork; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Owner {
        private String login;

        public String getLogin() { return login; }
    }
}