package fi.donhut.koinly.ftxusdfix.ftx;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
public class FTXWithdrawCsvRow extends AbstractFTXCsvRow {

    @Override
    public boolean isComplete() {
        return "complete".equalsIgnoreCase(getStatus());
    }

    @Override
    public Instant getTime() {
        return LocalDateTime.parse(getTimeS().substring(0, 26)).toInstant(ZoneOffset.UTC);
    }
}
