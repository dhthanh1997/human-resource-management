package com.ansv.humanresource.dto.specification;

import com.ansv.humanresource.dto.criteria.SearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

import static com.ansv.humanresource.dto.criteria.SearchOperation.*;
import static com.ansv.humanresource.dto.criteria.SearchOperation.getDataOption;

public class GenericSpecificationBuilder<T> {
    private final List<SearchCriteria> params;

    private Class<T> clazz;

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public GenericSpecificationBuilder(final List<SearchCriteria> params) {
        super();
        this.params = params;
    }

    public GenericSpecificationBuilder() {
        this.params = new ArrayList<>();
    }

    public final GenericSpecificationBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key,operation,value));
        return this;
    }

    public final GenericSpecificationBuilder with(SearchCriteria searchCriteria) {
        params.add(searchCriteria);
        return this;
    }



    public Specification<T> build() {
        if(params.size() == 0) {
            return null;
        }


        Specification<T> result = new GenericSpecification(params.get(0), clazz);

        for(int id = 1; id < params.size(); id ++) {
            SearchCriteria searchCriteria = params.get(id);

            switch (getDataOption(searchCriteria.getOperation())) {
                case ANY:
                    result = Specification.where(result).or(new GenericSpecification<T>(searchCriteria, clazz));
                    break;
                case ALL:
                default:
                  result =  Specification.where(result).and(new GenericSpecification<T>(searchCriteria, clazz));
                    break;
            }

//            result = getDataOption(searchCriteria.getOperation()) == ALL
//                    ? Specification.where(result).and(new GenericSpecification<T>(searchCriteria, clazz))
//                    : Specification.where(result).or(new GenericSpecification<T>(searchCriteria, clazz));
        }

        return result;
    }
}
