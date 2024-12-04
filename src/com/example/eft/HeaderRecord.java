public class HeaderRecord {
    private String logicalRecordType = "A";
    private String logicalRecordCount = "000000001";
    private String originatorNumber;
    private String fileCreationNumber;
    private String fileCreationDate;
    private String destinationDataCenter;
    private String currencyIdentifier = "CAD";
    private String filler = " ".repeat(1406);

    public HeaderRecord(String originatorNumber, String fileCreationNumber, String fileCreationDate, String destinationDataCenter) {
        this.originatorNumber = originatorNumber;
        this.fileCreationNumber = fileCreationNumber;
        this.fileCreationDate = fileCreationDate;
        this.destinationDataCenter = destinationDataCenter;
    }

    public String format() {
        return String.format("%-1s%-9s%-10s%-4s%-6s%-5s%-20s%-3s%s",
                logicalRecordType, logicalRecordCount, originatorNumber, fileCreationNumber,
                fileCreationDate, destinationDataCenter, "", currencyIdentifier, filler);
    }
} 