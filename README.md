# Euler CLJC

Solutions to problems from [Project Euler](https://projecteuler.net) written in portable Clojure[script].

This project is based on from the [Advent of CLJC](https://github.com/borkdude/advent-of-cljc) project.

## Contribute

PRs welcome. Make a new solution file with the `new` script:

    script/new 012 username

where `username` is your Github or Bitbucket username. Then fill in the solution in the file. If the input and answers are still empty you will have to provide it in `data.cljc`.

This repo does not accept multiple inputs and answers.

## Dev

Read [here](https://nrepl.xyz/nrepl/usage/server.html) how to get an nREPL for this project.

## Tests

Make sure the tests for your solution pass with the `test-one` script.

Please do not run calculations outside the tests. Memoized functions are permitted. Top-level lazy sequences are fine as long as they are not realized outside the tests.

Tests support the following metadata:

 - `:skip-cljs`: used for skipping Node tests. Used in `.circle/test-diff`,
   `script/test` and `script/test-one`.
 - `:slow`: used for skipping long running tests. Only used in `script/test`.

Run all tests:

    script/test

Run one test:

    script/test-one 012 username
    
Run with instrumentation:

    INSTRUMENT=true script/test
    INSTRUMENT=true script/test-one euler.012.username

Skip Clojure or ClojureScript:

    SKIP_CLJ=true script/test
    SKIP_CLJS=true script/test

