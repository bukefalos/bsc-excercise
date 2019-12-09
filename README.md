# bsc-excercise

[![Build Status](https://travis-ci.org/bukefalos/bsc-excercise.svg?branch=master)](https://travis-ci.org/bukefalos/bsc-excercise)

Postal package program exercise which is able to:

 - process new postal packages by user input
 - process initial file of postal packages by program argument
 - process fee structure for package weight by program argument
 - show summary of registered packages grouped by postal code by scheduled service at fixed 60 second intervals

## Requirements

 - JAVA 8+

## Build
In order to prepare run scripts run in project root:

    ./gradlew clean install
    
After installation was successful, find binaries at `build/install/postalservice/bin` directory relative to root.

   
## Usage
To run program

    usage: postalservice [-F <FILE>] [-I <FILE>]
     -F,--fees <FILE>   Define fee structure file input. Format of file should
                        for each line:
                        <weight><space><fee>
                        weight: integer|decimal(up to 3 decimal spaces)
                        fee: decimal(fixed 2 decimal spaces)
     -I,--init <FILE>   Define initial packages file input. Format of file
                        should for each line:<weight><space><postal_code>
                        weight: integer|decimal(up to 3 decimal spaces)
                        postal_code: integer-char(5 digits)
         
Program handles user input in format:
 
 - *WEIGHT* *POSTALCODE* (separated by single space)
    - *WEIGHT* can be positive decimal number up to 3 decimal digits
    - *POSTALCODE* is fixed 5 digit number
    - examples:  `12.3 04011`, `15.123 04022`
 - `quit` to quit program  
              