package fi.donhut.koinly.ftxusdfix.ftx;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class AbstractFTXCsvRow {

    private static final List<String> USD_COINS_AUTO_CONVERT = Arrays.asList("USDC", "TUSD", "PAX", "BUSD", "HUSD");

    @CsvBindByName(column = "Time")
    private String timeS;
    @CsvBindByName(column = "Status")
    private String status;
    @CsvBindByName(column = "Transaction ID")
    private String transactionId;

    @CsvBindByName(column = "Coin")
    private String coin;

    @CsvBindByName(column = "Amount")
    private BigDecimal amount;

    public boolean isUSDAutoCovertCoin() {
        return USD_COINS_AUTO_CONVERT.contains(getCoin());
    }

    public abstract boolean isComplete();
    public abstract Instant getTime();
}
