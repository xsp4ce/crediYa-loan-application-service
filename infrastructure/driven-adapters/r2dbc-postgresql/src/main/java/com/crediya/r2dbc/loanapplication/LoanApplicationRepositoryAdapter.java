package com.crediya.r2dbc.loanapplication;

import com.crediya.model.constants.LogMessages;
import com.crediya.model.loanapplication.LoanApplication;
import com.crediya.model.loanapplication.gateways.LoanApplicationRepository;
import com.crediya.r2dbc.entity.LoanApplicationEntity;
import com.crediya.r2dbc.helper.ReactiveAdapterOperations;
import lombok.extern.log4j.Log4j2;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Mono;

@Log4j2
@Repository
public class LoanApplicationRepositoryAdapter extends ReactiveAdapterOperations<LoanApplication,
 LoanApplicationEntity, Long, ILoanApplicationRepository> implements LoanApplicationRepository {
	private final TransactionalOperator transactionalOperator;

	public LoanApplicationRepositoryAdapter(
	 ILoanApplicationRepository repository, ObjectMapper mapper, TransactionalOperator transactionalOperator) {
		super(repository, mapper, d -> mapper.map(d, LoanApplication.class));
		this.transactionalOperator = transactionalOperator;
	}

	@Override
	public Mono<LoanApplication> save(LoanApplication loanApplication) {
		log.info(LogMessages.SAVING_APPLICATION_IN_DATABASE);
		return super.save(loanApplication).as(transactionalOperator::transactional);
	}
}
