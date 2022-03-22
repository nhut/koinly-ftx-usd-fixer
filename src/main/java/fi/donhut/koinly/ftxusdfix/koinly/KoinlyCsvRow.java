package fi.donhut.koinly.ftxusdfix.koinly;

import com.opencsv.bean.CsvBindByName;
import fi.donhut.koinly.ftxusdfix.ftx.FTXUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
public class KoinlyCsvRow {

    @CsvBindByName(column = "Date")
    private String timeS;
    @CsvBindByName(column = "Label")
    private String label;
    @CsvBindByName(column = "Description")
    private String description;
    @CsvBindByName(column = "TxHash")
    private String transactionId;

    @CsvBindByName(column = "Sent Amount")
    private BigDecimal sentAmount;
    @CsvBindByName(column = "Sent Currency")
    private String sentCurrency;

    @CsvBindByName(column = "Received Amount")
    private BigDecimal receivedAmount;
    @CsvBindByName(column = "Received Currency")
    private String receivedCurrency;

    public void setTime(final Instant time) {
        setTimeS(FTXUtil.DATE_TIME_FORMATTER.format(time));
    }
}
