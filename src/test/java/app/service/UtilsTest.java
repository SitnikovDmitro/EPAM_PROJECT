package app.service;


import app.dto.PaginationDTO;
import app.enums.BookGenre;
import app.enums.BookLanguage;
import app.enums.OrderState;

import app.service.other.*;
import app.service.other.impl.CustomTemplateImpl;
import app.service.utils.CryptographicUtil;
import app.service.utils.EnumerationUtil;
import app.service.utils.FormatUtil;
import app.service.utils.PaginationUtil;
import app.service.utils.impl.CryptographicUtilImpl;
import app.service.utils.impl.EnumerationUtilImpl;
import app.service.utils.impl.FormatUtilImpl;
import app.service.utils.impl.PaginationUtilImpl;
import app.utils.QueryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class UtilsTest {
    private final FormatUtil formatUtil;
    private final EnumerationUtil enumerationUtil;
    private final CryptographicUtil cryptographicUtil;
    private final PaginationUtil paginationUtil;


    public UtilsTest() {
        formatUtil = new FormatUtilImpl();
        enumerationUtil = new EnumerationUtilImpl();
        cryptographicUtil = new CryptographicUtilImpl();
        paginationUtil = new PaginationUtilImpl();
    }

    @Test
    public void formatFractionTest() {
        Assertions.assertEquals(formatUtil.formatFraction(1, 2), "0.5");
        Assertions.assertEquals(formatUtil.formatFraction(3, 3), "1");
        Assertions.assertEquals(formatUtil.formatFraction(1, 4), "0.25");
        Assertions.assertEquals(formatUtil.formatFraction(5, 2), "2.5");

        Assertions.assertEquals(formatUtil.formatAverageGrade(3, 3), "1");
        Assertions.assertEquals(formatUtil.formatAverageGrade(5, 2), "2.5");
    }

    @Test
    public void formatPriceTest() {
        Assertions.assertEquals(formatUtil.formatPrice(100), "1");
        Assertions.assertEquals(formatUtil.formatPrice(1200), "12");
        Assertions.assertEquals(formatUtil.formatPrice(1), "0.01");
        Assertions.assertEquals(formatUtil.formatPrice(0), "0");
        Assertions.assertEquals(formatUtil.formatPrice(10), "0.1");
    }

    @Test
    public void getLocalizationKeyTest() {
        Assertions.assertEquals(enumerationUtil.getBookGenreLocalizationKey(BookGenre.COMEDY), "comedy");
        Assertions.assertEquals(enumerationUtil.getBookGenreLocalizationKey(BookGenre.HORROR), "horror");
        Assertions.assertEquals(enumerationUtil.getBookLanguageLocalizationKey(BookLanguage.ENGLISH), "english");
        Assertions.assertEquals(enumerationUtil.getBookLanguageLocalizationKey(BookLanguage.UKRAINIAN), "ukrainian");
        Assertions.assertEquals(enumerationUtil.getBookLanguageShortLocalizationKey(BookLanguage.UKRAINIAN), "ukr");
        Assertions.assertEquals(enumerationUtil.getBookLanguageShortLocalizationKey(BookLanguage.JAPANESE), "jpn");
        Assertions.assertEquals(enumerationUtil.getOrderStateLocalizationKey(OrderState.CONFIRMED), "confirmed");
        Assertions.assertEquals(enumerationUtil.getOrderStateLocalizationKey(OrderState.WAITING_FOR_CONFIRMATION), "waitingForConfirmation");
    }


    @Test
    public void paginateTest() {
        PaginationDTO pagination = paginationUtil.paginate(8, 100, 2);

        Assertions.assertEquals(1, pagination.getFirstPage());
        Assertions.assertEquals(5, pagination.getPreviousPages().get(0));
        Assertions.assertEquals(6, pagination.getPreviousPages().get(1));
        Assertions.assertEquals(7, pagination.getPreviousPage());
        Assertions.assertEquals(8, pagination.getPageNumber());
        Assertions.assertEquals(9, pagination.getNextPage());
        Assertions.assertEquals(10, pagination.getNextPages().get(0));
        Assertions.assertEquals(11, pagination.getNextPages().get(1));
        Assertions.assertEquals(50, pagination.getLastPage());
    }

    @Test
    public void queryBuilderTestCase1() {
        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select("*").
                             from("orders").
                             where("creationDate = ?").
                             order("deadlineDate", "ASC").
                             limit(1, 2).build();
        Assertions.assertEquals("SELECT * FROM orders WHERE creationDate = ? ORDER BY deadlineDate ASC LIMIT 1, 2;", sql);
    }

    @Test
    public void queryBuilderTestCase2() {
        QueryBuilder builder = new QueryBuilder();
        String sql = builder.select("COUNT(*)").
                from("books").
                order("title", null).
                limit(null, 2).build();
        Assertions.assertEquals("SELECT COUNT(*) FROM books ORDER BY title LIMIT 2;", sql);
    }

    @Test
    public void generateHashTest() {
        String result = cryptographicUtil.generateHash("abcd");

        Assertions.assertEquals(44, result.length());
    }

    @Test
    public void generateRandomCodeTest() {
        String result = cryptographicUtil.generateRandomCode();

        Assertions.assertEquals(20, result.length());
    }

    @Test
    public void generateTestCase1() {
        CustomTemplate template = new CustomTemplateImpl("A b [arg1], cll [arg2]! Hello, [username]!");
        Map<String, String> map = new HashMap<>();
        map.put("arg1", "c");
        map.put("arg2", "lls");
        map.put("username", "Dmitriy");
        Assertions.assertEquals("A b c, cll lls! Hello, Dmitriy!", template.generate(map));
    }

    @Test
    public void generateTestCase2() throws IOException {
        try (InputStream stream1 = getClass().getClassLoader().getResourceAsStream("templates/access-message-content.html")) {


            CustomTemplate template = new CustomTemplateImpl(new String(stream1.readAllBytes(), StandardCharsets.UTF_8));

            Map<String, String> parameters = new HashMap<>();
            parameters.put("link", "link1");
            parameters.put("descriptionText", "descriptionText1");
            parameters.put("buttonText", "buttonText1");

            Assertions.assertDoesNotThrow(() -> template.generate(parameters));
        }

    }
}
