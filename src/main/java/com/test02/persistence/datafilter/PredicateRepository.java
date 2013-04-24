package com.test02.persistence.datafilter;

import com.google.common.base.Predicate;
import com.test02.model.Programme;
import org.joda.time.DateTime;

import java.util.Date;

public class PredicateRepository {

    public static Predicate<Programme> withChannel(final String channel) {
        Predicate predicate = new Predicate<Programme>() {
            @Override
            public boolean apply(Programme programme) {
                return channel != null ? channel.equals(programme.getChannel()) : true;
            }
        };

        return predicate;
    }

	public static Predicate<Programme> startDateFrom(final Date dateFrom) {
		Predicate predicate = new Predicate<Programme>() {
			@Override
			public boolean apply(Programme programme) {
				if (dateFrom == null)
					return true;
				if (programme.getStartTime() == null)
					return false;
				
				return  programme.getStartTime().after(dateFrom) || programme.getStartTime().equals(dateFrom);
			}
		};

		return predicate;
	}

	public static Predicate<Programme> startDateTo(final Date dateTo) {
		Predicate predicate = new Predicate<Programme>() {
			@Override
			public boolean apply(Programme programme) {
				if (dateTo == null)
					return true;
				if (programme.getStartTime() == null)
					return false;

				return  programme.getStartTime().before(dateTo) || programme.getStartTime().equals(dateTo);
			}
		};

		return predicate;
	}
}
