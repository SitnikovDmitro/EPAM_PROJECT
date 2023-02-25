package app.service.other.impl;

import app.database.dao.BookDAO;
import app.database.dao.OrderDAO;
import app.database.dao.UserDAO;
import app.entity.Book;
import app.entity.User;
import app.enums.BookSortCriteria;
import app.enums.OrderState;
import app.enums.UserRole;
import app.exceptions.DatabaseException;
import app.service.other.Localizator;
import app.service.other.XlsxGenerationService;
import app.service.utils.FormatUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class XlsxGenerationServiceImpl implements XlsxGenerationService {
    private final UserDAO userDAO;
    private final BookDAO bookDAO;
    private final OrderDAO orderDAO;
    private final FormatUtil formatUtil;
    private final Localizator localizator;

    public XlsxGenerationServiceImpl(UserDAO userDAO, BookDAO bookDAO, OrderDAO orderDAO, FormatUtil formatUtil, Localizator localizator) {
        this.userDAO = userDAO;
        this.bookDAO = bookDAO;
        this.orderDAO = orderDAO;
        this.formatUtil = formatUtil;
        this.localizator = localizator;
    }

    @Override
    public byte[] generateBookDataXlsxTable(String language) throws DatabaseException, IOException {
        List<Book> books = bookDAO.findBooks(null, null, null, null, null, null, null, null, BookSortCriteria.ALPHABET, 1, Integer.MAX_VALUE);

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(localizator.localize(language, "books"));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        Row header = sheet.createRow(0);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue(localizator.localize(language, "bookDataTable"));
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCell.setCellStyle(headerCellStyle);
        fillBooksInformationHeader(sheet.createRow(1), language);

        int i = 2;
        for (Book book : books) {
            fillBooksInformationRow(book, sheet.createRow(i++));
        }

        int lastRow = books.size()+1;
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, new CellRangeAddress(0, 0, 0, 6), sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, new CellRangeAddress(1, 1, 0, 6), sheet);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        RegionUtil.setBorderTop(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        for (int j = 0; j < 6; j++) {
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, new CellRangeAddress(1, lastRow, j, j), sheet);
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbook.write(stream);
        return stream.toByteArray();
    }

    @Override
    public byte[] generateReaderDataXlsxTable(String language) throws DatabaseException, IOException {
        List<User> readers = userDAO.findUsers(null, UserRole.READER, false, false, 1, Integer.MAX_VALUE);

        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet(localizator.localize(language, "readers"));

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        Row header = sheet.createRow(0);
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue(localizator.localize(language, "readerDataTable"));
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
        headerCell.setCellStyle(headerCellStyle);
        fillReadersInformationHeader(sheet.createRow(1), language);

        int i = 2;
        for (User reader : readers) {
            fillReadersInformationRow(reader, sheet.createRow(i++));
        }

        int lastRow = readers.size()+1;
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, new CellRangeAddress(0, 0, 0, 6), sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, new CellRangeAddress(1, 1, 0, 6), sheet);
        RegionUtil.setBorderLeft(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        RegionUtil.setBorderRight(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        RegionUtil.setBorderTop(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        RegionUtil.setBorderBottom(BorderStyle.MEDIUM, new CellRangeAddress(0, lastRow, 0, 6), sheet);
        for (int j = 0; j < 6; j++) {
            RegionUtil.setBorderRight(BorderStyle.MEDIUM, new CellRangeAddress(1, lastRow, j, j), sheet);
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
        sheet.autoSizeColumn(6);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        workbook.write(stream);
        return stream.toByteArray();
    }

    private void fillBooksInformationHeader(Row header, String lang) throws DatabaseException {
        Cell cell = header.createCell(0);
        cell.setCellValue(localizator.localize(lang, "isbn"));

        cell = header.createCell(1);
        cell.setCellValue(localizator.localize(lang, "title"));

        cell = header.createCell(2);
        cell.setCellValue(localizator.localize(lang, "freeCopies"));

        cell = header.createCell(3);
        cell.setCellValue(localizator.localize(lang, "totalCopies"));

        cell = header.createCell(4);
        cell.setCellValue(localizator.localize(lang, "waitingOrders"));

        cell = header.createCell(5);
        cell.setCellValue(localizator.localize(lang, "confirmedOrders"));

        cell = header.createCell(6);
        cell.setCellValue(localizator.localize(lang, "closedOrders"));
    }

    private void fillBooksInformationRow(Book book, Row row) throws DatabaseException {
        Cell cell = row.createCell(0);
        cell.setCellValue(book.getIsbn());

        cell = row.createCell(1);
        cell.setCellValue(book.getTitle());

        cell = row.createCell(2);
        cell.setCellValue(book.getFreeCopiesNumber());

        cell = row.createCell(3);
        cell.setCellValue(book.getTotalCopiesNumber());

        cell = row.createCell(4);
        cell.setCellValue(orderDAO.countOrders(null, null, book.getId(), OrderState.WAITING_FOR_CONFIRMATION));

        cell = row.createCell(5);
        cell.setCellValue(orderDAO.countOrders(null, null, book.getId(), OrderState.CONFIRMED));

        cell = row.createCell(6);
        cell.setCellValue(orderDAO.countOrders(null, null, book.getId(), OrderState.CLOSED));
    }


    private void fillReadersInformationHeader(Row header, String lang) throws DatabaseException {
        Cell cell = header.createCell(0);
        cell.setCellValue(localizator.localize(lang, "email"));

        cell = header.createCell(1);
        cell.setCellValue(localizator.localize(lang, "fullname"));

        cell = header.createCell(2);
        cell.setCellValue(localizator.localize(lang, "blocked"));

        cell = header.createCell(3);
        cell.setCellValue(localizator.localize(lang, "fine"));

        cell = header.createCell(4);
        cell.setCellValue(localizator.localize(lang, "waitingOrders"));

        cell = header.createCell(5);
        cell.setCellValue(localizator.localize(lang, "confirmedOrders"));

        cell = header.createCell(6);
        cell.setCellValue(localizator.localize(lang, "closedOrders"));
    }

    private void fillReadersInformationRow(User user, Row row) throws DatabaseException {
        Cell cell = row.createCell(0);
        cell.setCellValue(user.getEmail());

        cell = row.createCell(1);
        cell.setCellValue(user.getFirstname()+" "+user.getLastname());

        cell = row.createCell(2);
        cell.setCellValue(user.isBlocked());

        cell = row.createCell(3);
        cell.setCellValue(formatUtil.formatPrice(user.getFine()));

        cell = row.createCell(4);
        cell.setCellValue(orderDAO.countOrders(null, user.getId(), null, OrderState.WAITING_FOR_CONFIRMATION));

        cell = row.createCell(5);
        cell.setCellValue(orderDAO.countOrders(null, user.getId(), null, OrderState.CONFIRMED));

        cell = row.createCell(6);
        cell.setCellValue(orderDAO.countOrders(null, user.getId(), null, OrderState.CLOSED));
    }
}
