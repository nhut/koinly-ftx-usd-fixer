# koinly-ftx-usd-fixer
Fixes Koinly with FTX stable coin USD issue.

### Pre-requirement:
- Java 8 installed

### How to
1. Go to https://ftx.com/wallet
2. Click "Deposit"-tab.
3. Click "Download CSV" button.
4. Save the file "deposits.csv" to here (where this README.md-file is located).
5. Click "Withdraw"-tab.
6. Click "Download CSV" button.
7. Save the file "withdrawals.csv" to here (where this README.md-file is located).
8. Run following command in command line or terminal:
* (Windows) mvnw spring-boot:run
* (Linux) ./mvnw spring-boot:run
9. Open web browser and go to your Koinly FTX wallet (https://app.koinly.io/p/wallets)
10. Click "Import file" and then drag and drop file to it.
11. Click "Import" button and then you are done.
