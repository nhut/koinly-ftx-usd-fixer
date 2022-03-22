package fi.donhut.koinly.ftxusdfix;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fi.donhut.koinly.ftxusdfix.ftx.FileType;

import java.io.IOException;

public interface CsvService {
    String CURRENCY_USD = "USD";

    void readCsvFileAndCreateFixCsvFile(final FileType fileType, final String inputFile, final String outputFile) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;
}
