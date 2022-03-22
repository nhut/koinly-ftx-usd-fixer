package fi.donhut.koinly.ftxusdfix;

import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fi.donhut.koinly.ftxusdfix.ftx.AbstractFTXCsvRow;
import fi.donhut.koinly.ftxusdfix.ftx.FTXDepositCsvRow;
import fi.donhut.koinly.ftxusdfix.ftx.FTXWithdrawCsvRow;
import fi.donhut.koinly.ftxusdfix.ftx.FileType;
import fi.donhut.koinly.ftxusdfix.koinly.KoinlyCsvRow;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class CsvServiceImpl implements CsvService {

    public void readCsvFileAndCreateFixCsvFile(final FileType fileType, final String fileName, final String outputFilename)
            throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        final File outputFile = new File(outputFilename);
        if (outputFile.exists()) {
            if (!outputFile.delete()) {
                throw new RuntimeException("Unable to delete exists file: " + outputFile);
            }
        }

        final List<AbstractFTXCsvRow> rows = new CsvToBeanBuilder<AbstractFTXCsvRow>(new FileReader(fileName))
                .withType(fileType == FileType.WITHDRAW ? FTXWithdrawCsvRow.class : FTXDepositCsvRow.class)
                .build().parse();

        final List<KoinlyCsvRow> fixRows = getFixRows(fileType, rows);
        if (fixRows.isEmpty()) {
            log.debug("Nothing to write out.");
        }

        try (Writer writer = new FileWriter(outputFilename)) {
            new StatefulBeanToCsvBuilder<KoinlyCsvRow>(writer).build()
                    .write(fixRows);
        }
    }

    private static List<KoinlyCsvRow> getFixRows(final FileType fileType, final List<AbstractFTXCsvRow> rows) {
        final List<KoinlyCsvRow> fixRows = new ArrayList<>();
        for (AbstractFTXCsvRow originalRow : rows) {
            if (originalRow.isComplete() && originalRow.isUSDAutoCovertCoin()) {
                final KoinlyCsvRow fixRow = new KoinlyCsvRow();
                fixRow.setDescription("Koinly fix for FTX stable coin auto convert.");
                fixRow.setTransactionId(originalRow.getTransactionId());
                fixRow.setSentAmount(originalRow.getAmount());
                fixRow.setReceivedAmount(originalRow.getAmount());
                fixRow.setLabel("Swap");

                Instant rowFixTime = originalRow.getTime();
                if (fileType == FileType.WITHDRAW) {
                    rowFixTime = rowFixTime.minusSeconds(1);
                    fixRow.setSentCurrency(CsvService.CURRENCY_USD);
                    fixRow.setReceivedCurrency(originalRow.getCoin());

                } else if (fileType == FileType.DEPOSIT) {
                    rowFixTime = rowFixTime.plusSeconds(1);
                    fixRow.setSentCurrency(originalRow.getCoin());
                    fixRow.setReceivedCurrency(CsvService.CURRENCY_USD);
                }
                fixRow.setTime(rowFixTime);

                fixRows.add(fixRow);
            }
        }
        return fixRows;
    }
}
