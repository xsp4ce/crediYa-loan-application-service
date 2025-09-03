package com.crediya.api.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidateDocumentRequest {
	private String documentNumber;
	private Long idUser;
}
