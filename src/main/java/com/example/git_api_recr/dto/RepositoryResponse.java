package com.example.git_api_recr.dto;


import java.util.List;

public record RepositoryResponse(String repositoryName, String ownerLogin, List<BranchResponse> branches) {

}
