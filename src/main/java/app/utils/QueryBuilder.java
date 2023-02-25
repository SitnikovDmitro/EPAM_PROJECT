package app.utils;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Builder for complex sql queries
 **/
public class QueryBuilder {
    private String selection;
    private String table;
    private String whereCondition;
    private String orderCriteria;
    private String orderType;
    private Integer offset;
    private Integer count;

    /**
     * Specifies selected columns to be returned as a result of this sql query
     * @param selection selection expression
     **/
    public QueryBuilder select(String selection) {
        this.selection = selection;
        return this;
    }

    /**
     * Specifies table from which results
     * @param table table name
     **/
    public QueryBuilder from(String table) {
        this.table = table;
        return this;
    }

    /**
     * Specifies condition for filtering results
     * @param condition condition for filtering results
     **/
    public QueryBuilder where(String condition) {
        this.whereCondition = condition;
        return this;
    }

    /**
     * Specifies sort criteria and sort type
     * @param criteria name of column to sort by
     * @param type ascending (ASC) or descending (DESC) sort type
     **/
    public QueryBuilder order(String criteria, String type) {
        this.orderCriteria = criteria;
        this.orderType = type;
        return this;
    }

    /**
     * Specifies offset and size of area that will be returned as a result of sql query
     * Uses for pagination of result
     * @param offset offset
     * @param count count
     **/
    public QueryBuilder limit(Integer offset, Integer count) {
        this.offset = offset;
        this.count = count;
        return this;
    }

    /**
     * Builds sql query
     **/
    public String build() {
        if (empty(selection)) throw new RuntimeException("Selection required");
        if (empty(table)) throw new RuntimeException("Table required");
        if (count == null && offset != null) throw new RuntimeException("Count required if offset presents");
        if (empty(orderCriteria) && !empty(orderType)) throw new RuntimeException("Order criteria required if order type presents");


        StringBuilder sql = new StringBuilder();

        sql.append("SELECT ").append(selection).append(" FROM ").append(table);

        if (!empty(whereCondition)) {
            sql.append(" WHERE ").append(whereCondition);
        }

        if (!empty(orderCriteria)) {
            sql.append(" ORDER BY ").append(orderCriteria);
            if (!empty(orderType)) {
                sql.append(" ").append(orderType);
            }
        }

        if (count != null) {
            sql.append(" LIMIT ");
            if (offset != null) {
                sql.append(offset).append(", ");
            }
            sql.append(count);
        }

        return sql.append(";").toString();
    }

    private boolean empty(String value) {
        return value == null || value.isBlank();
    }
}
