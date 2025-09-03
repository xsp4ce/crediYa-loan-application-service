package com.crediya.api.mapper;

import com.crediya.api.dto.SaveLoanRequestDTO;
import com.crediya.model.command.SaveApplicationCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ILoanMapper {
	SaveApplicationCommand toCommand(SaveLoanRequestDTO saveLoanRequestDTO);
}
