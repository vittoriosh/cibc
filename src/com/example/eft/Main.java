import java.io.*;
import java.util.*;

public class Main {
    private static long totalCreditValue = 0;
    private static long totalDebitValue = 0;
    private static int totalCreditTransactions = 0;
    private static int totalDebitTransactions = 0;

    public static void main(String[] args) {
        List<String> creditRecords = processTransactions("credit2.csv", "C", "1234567890", "0001");
        List<String> debitRecords = processTransactions("debit.csv", "D", "1234567890", "0001");

        HeaderRecord header = new HeaderRecord("1234567890", "0001", "20231001", "01020");

        long totalRecords = creditRecords.size() + debitRecords.size() + 2;
        String formattedTotalRecords = String.format("%09d", totalRecords);

        TrailerRecord trailer = new TrailerRecord(
            formattedTotalRecords, 
            "12345678900001",
            String.format("%014d", totalDebitValue), 
            String.format("%08d", totalDebitTransactions),
            String.format("%014d", totalCreditValue), 
            String.format("%08d", totalCreditTransactions)
        );

        writeDatFile("output.CPA005.P.dat", header, creditRecords, debitRecords, trailer);
    }

    private static List<String> processTransactions(String filePath, String recordType, 
                                                  String originatorNumber, String fileCreationNumber) {
        List<String> formattedRecords = new ArrayList<>();
        int recordCount = 2;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String formattedRecord = formatDetailRecord(recordType, recordCount++, 
                                                         originatorNumber, fileCreationNumber, 
                                                         line);
                formattedRecords.add(formattedRecord);
                processTransaction(recordType, extractValuesFromRecord(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return formattedRecords;
    }

    private static void processTransaction(String recordType, long amount) {
        if (recordType.equals("C")) {
            totalCreditValue += amount;
            totalCreditTransactions++;
        } else if (recordType.equals("D")) {
            totalDebitValue += amount;
            totalDebitTransactions++;
        }
    }

    private static long extractValuesFromRecord(String record) {
        try {
            String[] fields = record.split(",");
            String amountStr = fields[1].trim();
            return Long.parseLong(amountStr);
        } catch (Exception e) {
            return 0;
        }
    }

    private static String formatDetailRecord(String recordType, int recordCount, 
                                          String originatorNumber, String fileCreationNumber, 
                                          String transaction) {
        String header = String.format("%1s%09d%10s%4s", 
            recordType,
            recordCount,
            originatorNumber,
            fileCreationNumber
        );
        
        String formattedTransaction = transaction.replace(",", "");
        formattedTransaction = String.format("%-240s", formattedTransaction);
        
        String padding = " ".repeat(1200);
        
        return header + formattedTransaction + padding;
    }

    private static void writeDatFile(String filePath, HeaderRecord header, 
                                   List<String> creditRecords, List<String> debitRecords, 
                                   TrailerRecord trailer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            String headerStr = header.format();
            verifyRecordLength(headerStr, "Header");
            writer.write(headerStr);
            writer.newLine();
            
            for (String record : creditRecords) {
                verifyRecordLength(record, "Credit");
                writer.write(record);
                writer.newLine();
            }
            
            for (String record : debitRecords) {
                verifyRecordLength(record, "Debit");
                writer.write(record);
                writer.newLine();
            }
            
            String trailerStr = trailer.format();
            verifyRecordLength(trailerStr, "Trailer");
            writer.write(trailerStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void verifyRecordLength(String record, String type) {
        if (record.length() != 1464) {
            System.err.println("Invalid " + type + " record length: " + record.length());
        }
    }
}