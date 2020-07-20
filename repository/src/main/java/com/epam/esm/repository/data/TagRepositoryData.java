package com.epam.esm.repository.data;

public class TagRepositoryData {
    public static final String TAG_NAME = "tag_name";
    public static final String FIND_BY_NAME_QUERY = "SELECT t FROM Tag t WHERE t.tagName = :tag_name";
    public static final String FIND_WIDELY_TAGS_QUERY = "SELECT id, tag_name FROM tag WHERE id IN" +
                                                        "(SELECT tc.tag_id FROM tag_certificate tc " +
                                                        "JOIN order_certificate AS oc ON oc.certificate_id = tc.certificate_id " +
                                                        "JOIN orders AS o ON oc.order_id = o.id " +
                                                        "WHERE o.user_id = " +
                                                        "(SELECT u.id FROM users AS u " +
                                                        "JOIN orders AS o ON u.id = o.user_id " +
                                                        "GROUP BY u.id " +
                                                        "HAVING SUM(o.order_price) = " +
                                                        "(SELECT MAX(s) FROM " +
                                                        "(SELECT SUM(o.order_price) s FROM users AS u " +
                                                        "JOIN orders AS o ON u.id = o.user_id " +
                                                        "GROUP BY u.id) AS tp LIMIT(1))) " +
                                                        "GROUP BY o.user_id, tc.tag_id " +
                                                        "HAVING COUNT(tc.tag_id) = " +
                                                        "(SELECT MAX(tag_count) FROM " +
                                                        "(SELECT tc.tag_id, count(tc.tag_id) tag_count FROM tag_certificate tc " +
                                                        "JOIN order_certificate AS oc ON oc.certificate_id = tc.certificate_id " +
                                                        "JOIN orders AS o ON oc.order_id = o.id " +
                                                        "WHERE o.user_id = " +
                                                        "(SELECT u.id FROM users AS u " +
                                                        "JOIN orders AS o ON u.id = o.user_id " +
                                                        "GROUP BY u.id " +
                                                        "HAVING SUM(o.order_price) = " +
                                                        "(SELECT MAX(s) FROM " +
                                                        "(SELECT SUM(o.order_price) s FROM users AS u " +
                                                        "JOIN orders AS o ON u.id = o.user_id " +
                                                        "GROUP BY u.id) AS tp LIMIT(1)))" +
                                                        "GROUP BY tc.tag_id)" +
                                                        "AS max_tag))";
    private TagRepositoryData(){
    }
}
