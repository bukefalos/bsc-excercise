# bsc-excercise

[![Build Status](https://travis-ci.org/bukefalos/bsc-excercise.svg?branch=master)](https://travis-ci.org/bukefalos/bsc-excercise)
[![Coverage Status](https://coveralls.io/repos/github/bukefalos/bsc-excercise/badge.svg?branch=master)](https://coveralls.io/github/bukefalos/bsc-excercise?branch=master)

Postal package program exercise which is able to:

 - process new postal packages by user input
 - process initial file of postal packages by program argument
 - process fee structure for package weight by program argument
 - show summary of registered packages grouped by postal code by scheduled service at fixed 60 second intervals

## Requirements

 - JAVA 8+

## Build / Run
There are two options:

 1. Use one of released version with executable scripts from *release* section
 2. Build the most up-to date version with executable scripts by simply running following command in project root: 

 
    ./gradlew clean install
    
After installation was successful, find binaries at `build/install/postalservice/bin` directory relative to root.

   
## Usage
To run program, run `postalservice` script `bin` directory

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
              