package com.crediya.r2dbc.loanapplication;

import com.crediya.r2dbc.entity.LoanApplicationEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ILoanApplicationRepository extends ReactiveCrudRepository<LoanApplicationEntity, Long>,
 ReactiveQueryByExampleExecutor<LoanApplicationEntity> {
}
