package com.example.git_api_recr;


import com.example.git_api_recr.dto.BranchResponse;
import com.example.git_api_recr.dto.RepositoryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserReposIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenExistingUser_whenGetRepos_thenReturnNonForkReposWithBranches() {
        String username = "leramiao";

        ResponseEntity<RepositoryResponse[]> response = restTemplate.getForEntity(
                "/users/" + username + "/repos",
                RepositoryResponse[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        RepositoryResponse[] repos = response.getBody();
        assertNotNull(repos);

        for (RepositoryResponse repo : repos) {
            assertNotNull(repo.repositoryName(), "Repository name should not be null");
            assertNotNull(repo.ownerLogin(), "Owner login should not be null");
            assertEquals(username, repo.ownerLogin(), "Owner login should match username");

            assertNotNull(repo.branches(), "Branches list should not be null");

            for (BranchResponse branch : repo.branches()) {
                assertNotNull(branch.name(), "Branch name should not be null");
                assertNotNull(branch.lastCommitSha(), "Last commit SHA should not be null");
            }
        }
    }
}