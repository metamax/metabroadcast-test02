package com.test02.persistence.datafilter;

import com.google.common.base.Predicate;
import com.test02.model.Programme;

public class Predicates {

    public static Predicate<Programme> filteredByChannel(final String channel) {
        Predicate predicate = new Predicate<Programme>() {
            @Override
            public boolean apply(Programme programme) {
                return channel != null ? channel.equals(programme.getChannel()) : true;
            }
        };

        return predicate;
    }
}
