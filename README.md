# Euler CLJC

[![Build Status](https://dev.azure.com/denisfuenzalida/euler-cljc/_apis/build/status/dfuenzalida.euler-cljc?branchName=master)](https://dev.azure.com/denisfuenzalida/euler-cljc/_build/latest?definitionId=1&branchName=master)

This project contains Clojure[script] solutions to problems from [Project Euler](https://projecteuler.net) written in a portable fashion, so that the same code works in both in the JVM and Node.

This project is based on the [Advent of CLJC](https://github.com/borkdude/advent-of-cljc) project.

## Contributors

![dfuenzalida](https://projecteuler.net/profile/dfuenzalida.png)

## How to contribute

Clone this repo and find a [problem](https://projecteuler.net/archives) that you want to solve. Make a new solution file with the `new` script:

    script/new number username

where `number` is the problem number and `username` is your Github or Bitbucket username. Then fill in the solution in the file. If the input and answer are still empty you will have to provide its md5 hash in the file `data.cljc`, using the `md5` function provided. This is required to avoid storing the actual solutions in plain text in the repo.

Once you are comfortable with your solution, test it as described below and submit a pull request. If you have an account in Project Euler, you can also submit your profile image to be listed in the *Contributors* section.

## Development

Clojure emphasizes the importance of an interactive development style using the REPL. Read [this page](https://github.com/dfuenzalida/euler-cljc/wiki/How-to-launch-a-REPL-for-this-project) about how to get an REPL for this project.

This project contains a `utils` namespace with some utility functions that are commonly used (including things like parsing strings to integer/floating numbers and generating prime numbers). Feel free to re-use those in your solutions.

Some problems (eg. [problem 11](https://projecteuler.net/problem=11)) include some input data as part of the problem statement. In those cases, the input data is available as `input` in the `data` namespace of each problem ([example here](https://github.com/dfuenzalida/euler-cljc/blob/master/src/euler/p011/data.cljc)).

## Tests

Make sure the tests for your solution pass with the `test-one` script, eg: `./script/test-one 42 myusername`.

Please do not run calculations outside the tests. Memoized functions are permitted. Top-level lazy sequences are fine as long as they are not realized outside the tests.

Please aim to have a solution that takes less than 30 seconds (in your computer) per problem. If your implementation takes more than that it's generally an indication that you are either brute-forcing the solution, or maybe you forgot taking some hint into consideration.

Tests support the following metadata:

 - `:skip-cljs`: used for skipping Node tests. Used in `script/test` and `script/test-one`.
 - `:slow`: used for skipping long running tests. Only used in `script/test`.

Run all tests:

    script/test

Run one test:

    script/test-one 12 username
    
Skip Clojure or ClojureScript tests:

    SKIP_CLJ=true script/test
    SKIP_CLJS=true script/test

