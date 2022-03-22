package fi.donhut.koinly.ftxusdfix.ftx;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
public class FTXDepositCsvRow extends AbstractFTXCsvRow {

    @Override
    public boolean isComplete() {
        return "confirmed".equalsIgnoreCase(getStatus());
    }

    @Override
    public Instant getTime() {
        return new Date(getTimeS()).toInstant();
    }
}
