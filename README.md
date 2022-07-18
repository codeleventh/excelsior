# Assigment task

## The Problem

### Input data

You can find file example.csv that contains unsorted information about stock price statistics. The file has following
structure:

- date: trading day
- ticker: name of the stock ticker
- price_close: close price of the trading day (the price of the last transaction)
- price_high: max price of the trading day
- price_low: min price of the trading day
- price_open: open price of the trading day (the price of the first transaction)

For example:

|price_close|price_high|price_low|price_open|ticker|date|
|---|---|---|---|---|---|
|774.39|774.83|744.1|753.99|TSLA|9/24/2021|
|3249.84|3303.91|3240.03|3279.62|AMZN|1/7/2022|
|3389.9|3458.44|3384.93|3435.39|AMZN|12/27/2021|
|2889.8|2897.81|2707.02|2877|AMZN|1/24/2022|
|851.53|856.81|846.95|851.61|TSLA|1/29/2022|
|2792.5|2822.65|2748|2797.73|GOOG|1/14/2022|

### Task conditions

Write Java program that receives the input path to the csv file and the numbers N and M. Your program should output the
following information to the console for each ticker:

- Print Ticker name
- Print days count
- Print maximum price for all days
- Print minimum price for all days
- For the last M days print moving average value of N elements (format - `01.01.2022: 1000.00`)  

### Additional requirements

1. Please use only standard Java libraries (Java 8 and higher)
2. Take into account corner cases and cases of incorrect N and M (suppose that file format is always correct)
3. Tests are optional
4. Try to make your solution scalable (let's assume that in a real situation, the data volumes can be much larger, as
   well as the numbers M and N).
5. Add some comments or ReadMe.md file which explains your algorithm.  
   
### Explanations:  
Moving average (MA) of N elements calculates as sum of the close prices for previous N days divided by N.  
For instance, if N = 5, M = 3, and we have the following data:

```
day = 01.01.2022: price_close = 10.00  
day = 02.01.2022: price_close = 11.00  
day = 03.01.2022: price_close = 12.00  
day = 04.01.2022: price_close = 13.00  
day = 05.01.2022: price_close = 14.00  
day = 06.01.2022: price_close = 15.00  
day = 07.01.2022: price_close = 16.00  
day = 08.01.2022: price_close = 17.00  
day = 09.01.2022: price_close = 18.00  
day = 10.01.2022: price_close = 19.00  
```

Then we need to calculate MA for 08.01.2022 - 10.01.2022 and the results will be:

```
08.01.2022: MA = (12.00 + 13.00 + 14.00 + 15.00 + 16.00) / 5 = 14.00  
09.01.2022: MA = (13.00 + 14.00 + 15.00 + 16.00 + 17.00) / 5 = 15.00  
10.01.2022: MA = (14.00 + 15.00 + 16.00 + 17.00 + 18.00) / 5 = 16.00
```  

So you should print:

```
08.01.2022: 14.00  
09.01.2022: 15.00  
10.01.2022: 16.00
```  
