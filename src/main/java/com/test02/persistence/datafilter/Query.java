package com.test02.persistence.datafilter;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import java.util.ArrayList;
import java.util.List;

public class Query<T> {
	
	private List<Predicate<T>> predicates = new ArrayList<Predicate<T>>();
	
	public void addPredicate(Predicate<T> predicate){
		predicates.add(predicate);
	}

	public void addPredicateIfParameterPresent(Optional<? extends Object> parameter, Predicate<T> predicate){
		if (parameter.isPresent())
			predicates.add(predicate);
	}	
	
	public List<Predicate<T>> getPredicates() {
		return this.predicates;
	}
}
