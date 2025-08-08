package com.example.git_api_recr.controller;

import com.example.git_api_recr.service.UserReposService;
import com.example.git_api_recr.dto.RepositoryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserReposController {

    private final UserReposService gitHubService;

    public UserReposController(UserReposService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/{username}/repos")
    public ResponseEntity<List<RepositoryResponse>> getUserRepositories(@PathVariable String username) {
        List<RepositoryResponse> repositories = gitHubService.getUserRepositories(username);
        return ResponseEntity.ok(repositories);
    }
}
