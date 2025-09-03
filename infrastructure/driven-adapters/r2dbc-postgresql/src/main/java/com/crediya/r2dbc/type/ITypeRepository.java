package com.crediya.r2dbc.type;

import com.crediya.r2dbc.entity.LoanTypeEntity;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ITypeRepository extends ReactiveCrudRepository<LoanTypeEntity, Long>,
 ReactiveQueryByExampleExecutor<LoanTypeEntity> {
}
