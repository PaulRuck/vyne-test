package uk.me.ruck.vyne.model;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification implements Specification<Transaction> {

    private final Transaction criteria;

    public TransactionSpecification(Transaction criteria) {
        this.criteria=criteria;
    }

    @Override
    public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();
        if(criteria.getStatus() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), criteria.getStatus()));
        }
        if(criteria.getCurrency() != null) {
            predicates.add(criteriaBuilder.equal(root.get("currency"), criteria.getCurrency()));
        }
        if(criteria.getDescription() != null) {
            predicates.add(criteriaBuilder.like(root.get("description"), "%"+criteria.getDescription()+"%"));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}