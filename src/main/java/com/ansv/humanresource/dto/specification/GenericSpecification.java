package com.ansv.humanresource.dto.specification;

import com.ansv.humanresource.dto.criteria.SearchCriteria;
import com.ansv.humanresource.dto.criteria.SearchOperation;
import com.ansv.humanresource.util.DataUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;


    public GenericSpecification(final SearchCriteria searchCriteria, Class<T> clazz) {
        super();
        String dataType = findDataType(searchCriteria.getKey(), clazz);
        List<String> arrayIgnore = Arrays.asList(SearchOperation.IGNORE_OPERATION_SET);
        if (dataType.equals("Integer")) {
            if(!arrayIgnore.contains(searchCriteria.getOperation())) {
                searchCriteria.setValue(DataUtils.convertToDataType(Integer.class, searchCriteria.getValue().toString()));
                searchCriteria.setDataType(Integer.class);
            }
        }
        if (dataType.equals("Long")) {
            if(!arrayIgnore.contains(searchCriteria.getOperation())) {
                searchCriteria.setValue(DataUtils.convertToDataType(Long.class, searchCriteria.getValue().toString()));
                searchCriteria.setDataType(Long.class);
            }

        }
        if (dataType.equals("Double")) {
            if(!arrayIgnore.contains(searchCriteria.getOperation())) {
                searchCriteria.setValue(DataUtils.convertToDataType(Double.class, searchCriteria.getValue().toString()));
                searchCriteria.setDataType(Double.class);
            }


        }
        if (dataType.equals("String")) {
//            searchCriteria.setValue(DataUtils.convertToDataType(String.class, searchCriteria.getValue().toString()));
        }
        if (dataType.equals("Float")) {
            if(!arrayIgnore.contains(searchCriteria.getOperation())) {
                searchCriteria.setValue(DataUtils.convertToDataType(Float.class, searchCriteria.getValue().toString()));
                searchCriteria.setDataType(Float.class);
            }

        }

        if (dataType.equals("Byte")) {
            if(!arrayIgnore.contains(searchCriteria.getOperation())) {
                searchCriteria.setValue(DataUtils.convertToDataType(Byte.class, searchCriteria.getValue().toString()));
                searchCriteria.setDataType(Byte.class);
            }
        }

        this.searchCriteria = searchCriteria;
    }

    /**
     * find datatype with fieldname
     *
     * @param fieldName
     * @return
     */
    public String findDataType(String fieldName, Class<T> clazz) {
        String dataType = null;
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            if (fieldName.equals(field.getName())) {
                String[] str = field.getType().getTypeName().split(Pattern.quote("."));
                // str[2] = dataType
                dataType = str[2];
                break;
            }
        }
        return dataType;
    }

    @Override
    public Specification<T> and(Specification<T> other) {

        return Specification.super.and(other);
    }

    @Override
    public Specification<T> or(Specification<T> other) {
        return Specification.super.or(other);
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Object strToSearch = searchCriteria.getValue();
        switch (Objects.requireNonNull(SearchOperation.getSimpleOperation(searchCriteria.getOperation()))) {
            case CONTAINS:
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase() + "%");
            case DOES_NOT_CONTAIN:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase() + "%");

            case BEGINS_WITH:

                return cb.like(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase() + "%");
            case DOES_NOT_BEGIN_WITH:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase() + "%");
            case ENDS_WITH:
                return cb.like(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase());
            case DOES_NOT_END_WITH:
                return cb.notLike(cb.lower(root.get(searchCriteria.getKey())), "%" + strToSearch.toString().toLowerCase());
            case EQUAL:
                if (strToSearch instanceof String) {
                    return cb.equal(cb.lower(root.<String>get(searchCriteria.getKey())), strToSearch.toString().toLowerCase());
                } else {
                    return cb.equal(root.<Object>get(searchCriteria.getKey()), strToSearch);
                }

            case NOT_EQUAL:
                if (strToSearch instanceof String) {
                    return cb.notEqual(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase());
                } else {
                    return cb.notEqual(root.<Object>get(searchCriteria.getKey()), strToSearch);
                }
            case NUL:
                return cb.isNull((root.get(searchCriteria.getKey())));
            case NOT_NULL:
                return cb.isNotNull((root.get(searchCriteria.getKey())));
            case GREATER_THAN:
//                if (strToSearch instanceof String) {
//                }
//                else {
//                    return cb.greaterThan(root.get(searchCriteria.getKey()), strToSearch);
//                }
                return cb.greaterThan(cb.lower(root.get(searchCriteria.getKey())), strToSearch.toString().toLowerCase());

            case GREATER_THAN_EQUAL:
//                if (strToSearch instanceof String) {
//                }
//                else {
//                    return cb.greaterThanOrEqualTo(root.<Object>get(searchCriteria.getKey()), strToSearch);
//                }
                return cb.greaterThanOrEqualTo(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN:
                return cb.lessThan(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
            case LESS_THAN_EQUAL:
                return cb.lessThanOrEqualTo(root.<String>get(searchCriteria.getKey()), searchCriteria.getValue().toString());
        }
        return null;
    }
}
