package app.service.other;

import app.exceptions.DatabaseException;

import java.io.IOException;

/**
 * Xlsx files generation service
 **/
public interface XlsxGenerationService {
    /**
     * Creates books information table in xlsx file format containing
     * books ISBN, name, total copies number, free copies number and
     * number of waiting, confirmed and closed order of each book
     * @return xlsx file data in byte array
     **/
    byte[] generateBookDataXlsxTable(String language) throws DatabaseException, IOException;

    /**
     * Creates readers information table in xlsx file format containing
     * readers email, fullname, blocking status, amount of file (if exists) and
     * number of waiting, confirmed and closed order of each reader
     * @return xlsx file data in byte array
     **/
    byte[] generateReaderDataXlsxTable(String language) throws DatabaseException, IOException;
}
