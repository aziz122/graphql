package fr.codeonce.graphql.web.servlet.components;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.codeonce.graphql.web.servlet.ExecutionResultHandler;
import fr.codeonce.graphql.web.servlet.GraphQLInvocation;
import fr.codeonce.graphql.web.servlet.GraphQLInvocationData;
import graphql.ExecutionResult;
import graphql.Internal;

@RestController
@Internal
public class GraphQLController2 {

    @Autowired
    GraphQLInvocation graphQLInvocation;

    @Autowired
    ExecutionResultHandler executionResultHandler;

    @Autowired
    ObjectMapper objectMapper;

    @RequestMapping(value = "${graphql.url:graphql2}",
            method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object graphqlPOST(@RequestBody GraphQLRequestBody body,
                              WebRequest webRequest) {
        String query = body.getQuery();
        if (query == null) {
            query = "";
        }
        CompletableFuture<ExecutionResult> executionResult = graphQLInvocation.invoke(new GraphQLInvocationData(query, body.getOperationName(), body.getVariables()), webRequest);
        return executionResultHandler.handleExecutionResult(executionResult);
    }

    @RequestMapping(value = "${graphql.url:graphql2}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object graphqlGET(
            @RequestParam("query") String query,
            @RequestParam(value = "operationName", required = false) String operationName,
            @RequestParam(value = "variables", required = false) String variablesJson,
            WebRequest webRequest) {
        CompletableFuture<ExecutionResult> executionResult = graphQLInvocation.invoke(new GraphQLInvocationData(query, operationName, convertVariablesJson(variablesJson)), webRequest);
        return executionResultHandler.handleExecutionResult(executionResult);
    }

    private Map<String, Object> convertVariablesJson(String jsonMap) {
        if (jsonMap == null) return Collections.emptyMap();
        try {
            return objectMapper.readValue(jsonMap, Map.class);
        } catch (IOException e) {
            throw new RuntimeException("Could not convert variables GET parameter: expected a JSON map", e);
        }

    }


}