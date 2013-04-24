package com.test02.persistence.datafilter;

import com.google.common.base.Predicate;
import com.test02.model.Programme;

import java.util.Date;

import static com.google.common.base.Preconditions.checkNotNull;

public class PredicateRepository {

    public static Predicate<Programme> withChannel(final String channel) {
        checkNotNull(channel);

        Predicate predicate = new Predicate<Programme>() {
            @Override
            public boolean apply(Programme programme) {
                return channel.equals(programme.getChannel());
            }
        };

        return predicate;
    }

	public static Predicate<Programme> startDateFrom(final Date dateFrom) {
        checkNotNull(dateFrom);

		Predicate predicate = new Predicate<Programme>() {
			@Override
			public boolean apply(Programme programme) {
				if (programme.getStartTime() == null) {
					return false;
                }
				
				return  programme.getStartTime().after(dateFrom) || programme.getStartTime().equals(dateFrom);
			}
		};

		return predicate;
	}

	public static Predicate<Programme> startDateTo(final Date dateTo) {
        checkNotNull(dateTo);

		Predicate predicate = new Predicate<Programme>() {
			@Override
			public boolean apply(Programme programme) {
				if (programme.getStartTime() == null) {
					return false;
                }

				return  programme.getStartTime().before(dateTo) || programme.getStartTime().equals(dateTo);
			}
		};

		return predicate;
	}
}
