

public class TrailerRecord {
    private String logicalRecordType = "Z";
    private String logicalRecordCount;
    private String originatorControlNumber;
    private String totalValueOfDebitTransactions;
    private String totalNumberOfDebitTransactions;
    private String totalValueOfCreditTransactions;
    private String totalNumberOfCreditTransactions;
    private String filler = " ".repeat(1352);

    public TrailerRecord(String logicalRecordCount, String originatorControlNumber,
                         String totalValueOfDebitTransactions, String totalNumberOfDebitTransactions,
                         String totalValueOfCreditTransactions, String totalNumberOfCreditTransactions) {
        this.logicalRecordCount = logicalRecordCount;
        this.originatorControlNumber = originatorControlNumber;
        this.totalValueOfDebitTransactions = totalValueOfDebitTransactions;
        this.totalNumberOfDebitTransactions = totalNumberOfDebitTransactions;
        this.totalValueOfCreditTransactions = totalValueOfCreditTransactions;
        this.totalNumberOfCreditTransactions = totalNumberOfCreditTransactions;
    }

    public String format() {
        return String.format("%-1s%-9s%-14s%-14s%-8s%-14s%-8s%s",
                logicalRecordType, logicalRecordCount, originatorControlNumber,
                totalValueOfDebitTransactions, totalNumberOfDebitTransactions,
                totalValueOfCreditTransactions, totalNumberOfCreditTransactions, filler);
    }
} 