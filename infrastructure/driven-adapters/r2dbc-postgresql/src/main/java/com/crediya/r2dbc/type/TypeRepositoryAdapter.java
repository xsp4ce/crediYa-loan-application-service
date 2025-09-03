package com.crediya.r2dbc.type;

import com.crediya.model.constants.LogMessages;
import com.crediya.model.exceptions.ValidationException;
import com.crediya.model.exceptions.ExceptionMessages;
import com.crediya.model.type.Type;
import com.crediya.model.type.gateways.TypeRepository;
import com.crediya.r2dbc.entity.LoanTypeEntity;
import com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class TypeRepositoryAdapter extends ReactiveAdapterOperations<Type, LoanTypeEntity, Long, ITypeRepository> implements TypeRepository {

	public TypeRepositoryAdapter(ITypeRepository repository, ObjectMapper mapper) {
		super(repository, mapper, d -> mapper.map(d, Type.class));
	}

	@Override
	public Mono<Type> findById(Long id) {
		log.info(LogMessages.FINDING_LOAN_TYPE, id);
		return super.findById(id)
		 .switchIfEmpty(Mono.error(new ValidationException(ExceptionMessages.TYPE_NOT_FOUND + id)));
	}
}
