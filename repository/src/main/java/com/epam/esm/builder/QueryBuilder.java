package com.epam.esm.builder;

import com.epam.esm.model.Certificate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static com.epam.esm.builder.QueryBuilderData.*;

@Component
public class QueryBuilder {
    public String buildQuery(Map<String, String> params, String tableName) {
        String sortingOrder = ASCENDING;
        String searchBy = ALL;
        String sortBy = ID;
        String searchByParam = null;
        for (String param : params.keySet()) {
            switch (param) {
                case SORT_BY:
                    sortBy = params.get(param);
                    if (String.valueOf(sortBy.charAt(0)).equals(MINUS)) {
                        sortBy = sortBy.substring(1);
                        sortingOrder = DESCENDING;
                    }
                    break;
                case EMAIL:
                case LOGIN:
                case TAG_NAME:
                case ORDER_OWNER_NAME:
                case CERTIFICATE_NAME:
                    searchBy = param;
                    searchByParam = params.get(param);
                    if (searchBy.equals(TAG_NAME) && tableName.equals(Certificate.class.getSimpleName())) {
                        searchBy = CERTIFICATE_TAG_NAME;
                    }
                    break;
            }
        }
        return buildQueryString(tableName, searchBy, searchByParam, sortBy, sortingOrder);
    }

    private String buildQueryString(String tableName,
                                    String searchBy,
                                    String searchByParam,
                                    String sortBy,
                                    String sortingOrder) {
        String sql;
        String select = getSelectString(tableName);
        String orderBy = getSortString(sortBy, sortingOrder, tableName);
        switch (searchBy) {
            case ALL:
                sql = getAllQuery(select, orderBy);
                break;
            case CERTIFICATE_TAG_NAME:
                sql = getCertificateByTagNameQuery(searchByParam, select, orderBy);
                break;
            default:
                sql = getDefaultQuery(select, searchBy, searchByParam, orderBy);
        }
        return sql;
    }

    private String getAllQuery(String select, String orderBy) {
        return select + orderBy;
    }

    private String getDefaultQuery(String select, String searchBy, String searchByParam, String orderBy) {
        StringBuilder query = new StringBuilder();
        query.append(select);
        query.append(WHERE_X);
        query.append(searchBy);
        query.append(LIKE_CONCAT);
        query.append(searchByParam);
        query.append(HOOKS);
        query.append(orderBy);
        return query.toString();
    }

    private String getCertificateByTagNameQuery(String searchByParam, String select, String orderBy) {
        List<String> tagNames = Arrays.asList(searchByParam.split(COMMA));
        StringJoiner joiner = new StringJoiner(COMMA);
        tagNames.forEach(tagName -> joiner.add(APOSTROPHE + tagName.trim() + APOSTROPHE));
        StringBuilder query = new StringBuilder();
        query.append(select);
        query.append(LEFT_JOIN_FETCH);
        query.append(joiner.toString());
        query.append(HAVING_COUNT);
        query.append(tagNames.size());
        query.append(HOOK);
        query.append(orderBy);
        return query.toString();
    }

    private String getSortString(String sortBy, String sortingOrder, String tableName) {
        if (!SortStringValidator.isValidSortData(sortBy, tableName)) {
            sortBy = ID;
        }
        StringBuilder query = new StringBuilder();
        query.append(ORDER_BY);
        query.append(sortBy);
        query.append(sortingOrder);
        System.out.println(query.toString());
        return query.toString();
    }

    private String getSelectString(String tableName) {
        StringBuilder query = new StringBuilder();
        query.append(SELECT_FROM);
        query.append(tableName);
        query.append(X);
        return query.toString();
    }
}
