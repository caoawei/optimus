package com.souche.elasticsearch;

import com.optimus.utils.Utils;
import com.souche.elasticsearch.query.EsBuilder;
import com.souche.elasticsearch.query.support.EsBool;
import com.souche.elasticsearch.query.support.EsFilter;
import com.souche.elasticsearch.query.support.EsFiltered;
import com.souche.elasticsearch.query.support.EsQuery;
import com.souche.elasticsearch.query.support.EsSearch;
import com.souche.elasticsearch.query.support.EsTerm;
import com.souche.elasticsearch.query.support.EsTerms;

public class Test {

    public static void main(String[] args) {
        EsFilter filter = EsBuilder.filter();
        EsBool bool = EsBuilder.bool();
        filter.bind(bool);
        bool.must(EsBuilder.keyValuePair("price",20));
        bool.must(EsBuilder.keyValuePair("price",30));

        bool.should(EsBuilder.keyValuePair("name","Tom"));
        bool.should(EsBuilder.keyValuePair("name","Jake"));

        bool.mustNot(EsBuilder.term(EsBuilder.keyValuePair("address","China")));
        bool.mustNot(EsBuilder.term(EsBuilder.keyValuePair("address","Japan")));


        EsFilter filter2 = EsBuilder.filter();
        filter2.bind(EsBuilder.term(EsBuilder.keyValuePair("address","China")));
        System.out.println(Utils.toJson(filter.toMap()));
        System.out.println("---------------------------------------------------------\n\n");
        System.out.println(Utils.toJson(filter2.toMap())+"\n");


        EsFilter filter3 = EsBuilder.filter();

        EsTerms terms = new EsTerms();
        terms.bind(EsBuilder.keyValuePair("price",20));

        filter3.bind(terms);

        System.out.println("\n---------------------------------------------------------\n\n");
        System.out.println(Utils.toJson(filter3.toMap())+"\n");


        // ============================================================ //

        EsQuery query = EsBuilder.query();
        EsFiltered filtered = EsBuilder.filtered();
        EsFilter filter4 = EsBuilder.filter();
        EsBool bool2 = EsBuilder.bool();
        EsTerm term = EsBuilder.term(EsBuilder.keyValuePair("age",25));
        EsTerms terms2 = EsBuilder.terms(EsBuilder.keyValuePair("price",30,40,50));

        bool2.must(term).should(terms2).mustNot(filter3);
        filter4.bind(bool2);
        filtered.bind(filter4);
        query.bind(filtered);

        EsSearch search = EsBuilder.search(query);
        System.out.println("\n---------------------------------------------------------\n\n");
        System.out.println(Utils.toJson(search.toMap())+"\n");
    }
}
