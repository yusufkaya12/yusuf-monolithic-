package com.threepounds.caseproject.util;

import co.elastic.clients.elasticsearch._types.query_dsl.MatchAllQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.util.ObjectBuilder;
import lombok.experimental.UtilityClass;

import java.util.function.Function;
import java.util.function.Supplier;

@UtilityClass
public class ESUtil {
    public static Query matchQuery(){
        return Query.of(q->q.matchAll(new MatchAllQuery.Builder().build()));
    }

    public static Supplier<Query> buildQuery(String fieldName,String searchName) {
        return ()->Query.of(q->q.match(buildMatchQueryForAdvert(fieldName,searchName)));

    }

    private static MatchQuery buildMatchQueryForAdvert(String fieldName, String searchName) {
        return new MatchQuery.Builder()
                .field(fieldName)
                .query(searchName)
                .build();
    }
}
