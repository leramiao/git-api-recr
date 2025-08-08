package com.example.git_api_recr.service;

import com.example.git_api_recr.dto.BranchGitHubDto;
import com.example.git_api_recr.dto.BranchResponse;
import com.example.git_api_recr.dto.RepositoryGitHubDto;
import com.example.git_api_recr.dto.RepositoryResponse;
import com.example.git_api_recr.excpetion.UserNotFoundException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserReposService {

    private final RestTemplate restTemplate;

    public UserReposService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<RepositoryResponse> getUserRepositories(String username) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/vnd.github+json");

            HttpEntity<String> entity = new HttpEntity<>(headers);

            String repoUrl = "https://api.github.com/users/" + username + "/repos?type=owner";
            ResponseEntity<RepositoryGitHubDto[]> repoResponse = restTemplate.exchange(
                    repoUrl,
                    HttpMethod.GET,
                    entity,
                    RepositoryGitHubDto[].class
            );

            RepositoryGitHubDto[] repos = repoResponse.getBody();
            if (repos == null) return Collections.emptyList();

            List<RepositoryResponse> result = new ArrayList<>();

            for (RepositoryGitHubDto repo : repos) {
                // Skipping forks
                if (repo.isFork()) continue;

                String branchesUrl = "https://api.github.com/repos/" + username + "/" + repo.getName() + "/branches";
                ResponseEntity<BranchGitHubDto[]> branchesResponse = restTemplate.exchange(
                        branchesUrl,
                        HttpMethod.GET,
                        entity,
                        BranchGitHubDto[].class
                );

                BranchGitHubDto[] branchesGitHub = branchesResponse.getBody();
                List<BranchResponse> branches = new ArrayList<>();

                if (branchesGitHub != null) {
                    for (BranchGitHubDto branch : branchesGitHub) {
                        branches.add(new BranchResponse(branch.getName(), branch.getCommit().getSha()));
                    }
                }

                result.add(new RepositoryResponse(repo.getName(), repo.getOwner().getLogin(), branches));
            }

            return result;

        } catch (HttpClientErrorException.NotFound e) {
            throw new UserNotFoundException("GitHub user not found");
        }
    }
}
