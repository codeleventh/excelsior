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

## The solution

First of all: we don't care how big the input file is. We read it by lines, and it has a memory consumption that grows in linear way.
Also, we don't have a strong need to save memory (there are not so many stocks in the real world, a few thousand at most), we can sacrifice some amount to improve readability instead.

Second, the data isn't sorted (I'm not sure if that happens IRL). Sorting would make the solution much easier, but we won't do that — it will increase the complexity to n log n instantly.

Here is the solution: we will remember N + M most recent tuples (date, priceClose) for each ticker, which will be used to calculate MA.
There is a child class of TreeSet, which will be responsible for storing and sorting our data. It has O(log n) for insertion and a limited capacity.

Calculation of min/max values is trivial.

There is also some optimization for MA calculation, though it's an overkill — it will be important only at a very large N (but who needs AM for 100000 days?)

The total performance complexity is the same as memory: O(n).
The MA calculation won't exceed that — although the loop statement is nested, the total number of operations cannot be greater than the records number (by definition).

BTW, the situation with missing data is still possible, for example, when M or N is greater than a total number of ticker records.
For this case, I decided to output as many numbers as possible.

NB: I used Lombok in a project and since it doesn't add any data structures and utils, but only reduces the boilerplate, I do not consider it as a violation :>

