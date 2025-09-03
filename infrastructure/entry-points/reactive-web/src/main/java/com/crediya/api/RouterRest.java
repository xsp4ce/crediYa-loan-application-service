package com.crediya.api;

import com.crediya.api.constants.LoanPaths;
import com.crediya.api.dto.SaveLoanRequestDTO;
import com.crediya.api.exception.CustomErrorResponse;
import com.crediya.model.constants.LogMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.core.annotations.RouterOperation;
import org.springdoc.core.annotations.RouterOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class RouterRest {
	@Bean
	@RouterOperations({
	 @RouterOperation(path = "/api/v1/request", method = RequestMethod.POST, operation = @Operation(operationId =
		"createLoanApplication", summary = "Create a new loan application", description =
		"Creates a new loan application in the system with the provided information", tags = {
		"Loan Management"}, requestBody = @RequestBody(description = "Loan application information to create", required =
		true,
		content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation =
		 SaveLoanRequestDTO.class), examples = @ExampleObject(name = "Loan application Example", value = """
		{
		    "documentNumber": "12345678",
		    "amount": 5000.00,
		    "term": 3,
		    "idLoanType": 1
		}
		"""))), responses = {
		@ApiResponse(responseCode = "201", description = "Loan application created successfully", content = @Content(mediaType =
		 MediaType.APPLICATION_JSON_VALUE)),
		@ApiResponse(responseCode = "400", description = "Validation error or bad request", content = @Content(mediaType =
		 MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorResponse.class), examples =
		@ExampleObject(name = "Validation Error", value = """
		 	{
		 		"code": "VALIDATION_ERROR",
		 		"message": "User not found with that document number"
		 	}
		 """))),
		@ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType =
		 MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = CustomErrorResponse.class), examples =
		@ExampleObject(name = "Internal Server Error", value = """
	 		 	{
	 		 		"code": "BUSINESS_ERROR",
	 		 		"message": "An unexpected error occurred"
	 		 	}
	 		 """)))
	 }))})
	public RouterFunction<ServerResponse> routerFunction(Handler handler) {
		log.info(LogMessages.CONSUMING_PATH_REQUEST);
		return route(POST(LoanPaths.REQUEST), handler::listenSaveLoanRequest);
	}
}
