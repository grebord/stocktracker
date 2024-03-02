# Stock Tracker
  
This is a modular JavaFX application that fetches bond and stock data from BYMA and Yahoo Finance APIs and presents it in a tidy manner.  
  
I made it so it would be useful to my particular needs, but it can be helpful to others willing to see how to extract data from these APIs or code a similar application.  
    
![app screenshot](/github/app.jpg)

When the application starts, it will start fetching all the data in sequence. Due to absurd rate limiting by BYMA, this takes a good while.  
It will first fetch bond data (AL30, AL30D, GD30D), two of which it needs to compute the current MEP dollar value.  
Then it will fetch the data for each stock in three parts: The stock data from American markets, the ARS CEDEAR data, and its USD CEDEAR counterpart. Below a separator it will present the international stock's price divided the CEDEAR ratio (UL), and below it will show the data and percentages comparing the CEDEARs to the underlying international asset, and one percentage comparing the USD CEDEAR price to the ARS one. All prices are in US dollars.  
  
### Running:
  
Run the application with the following launch parameters:  
```
--useApi=true XXX:## YYY:## ...
```
where XXX, YYY are stock symbols and ## are numbers representing CEDEAR ratios.  
  
In order to run it, you can create an executable file containing all modules, or use maven:  
```
(base folder) $ mvn clean install
(javafx fold) $ mvn javafx:run
```
For sad reasons, you will need to supply the Yahoo API parameters yourself: `api.host` and `api.path`, in the `yahoo.properties` file. Look for the HTTP request for option data in yahoo finance, or contact me and I will help you find an alternative.  
  
### Thanks
  
Thank you to the excellent JavaFX tutorials from www.pragmaticcoding.ca
