package fi.donhut.koinly.ftxusdfix;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import fi.donhut.koinly.ftxusdfix.ftx.FileType;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@Log4j2
@SpringBootApplication
public class FtxUsdFixApplication implements CommandLineRunner {

    @Autowired
    private CsvService csvService;

    public static void main(String[] args) {
        SpringApplication.run(FtxUsdFixApplication.class, args);
    }

    @Override
    public void run(final String... args) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        csvService.readCsvFileAndCreateFixCsvFile(FileType.DEPOSIT, "deposits.csv", "deposits-output.csv");
        csvService.readCsvFileAndCreateFixCsvFile(FileType.WITHDRAW, "withdrawals.csv", "withdrawals-output.csv");
        log.info("Done!");
    }

}
