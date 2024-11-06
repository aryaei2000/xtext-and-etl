## An ETL transformation to fold sequences of move statements

> You can [do this activity in your browser](https://ep.mde-network.org/?activities=https://raw.githubusercontent.com/epdemo/xtext-and-etl-szschaler/main/activity.json&privaterepo=true). Click on the link and your browser will open the MDENet Education Platform with the activity pre-loaded.

Building on your ETL copy transformation, change the transformation so that it folds sequences of `MoveStatement`s into a single `MoveStatement`. So, for example:

```
forward(20)
forward(10)
```

should become

```
forward(20+10)
```

Similarly, 

```
forward(10+5)
backward(9)
```

should become

```
forward(10+5-9)
```

Remember that ETL makes transformations at the level of the *abstract* syntax. You will need to
 
1. Change the rule that transforms `MoveStatement`s so that it doesn't simply copy every `MoveStatement` but only the first in each sequence;
2. Add code to collect the expressions from all `MoveStatement`s in a given sequence
3. Create a new `Addition` expression that combines all of these expressions together

Again, you will want to test your transformation in the runtime Eclipse. Eventually, save it in the file called `fold_moves.etl` in the `generator` package in the main `turtles` project so that the auto-grader can pick it up.

The auto-grader has a test that checks the basic requirement and some tests that check for corner cases (for example, where the `MoveStatement` is the first or the last in a block of statements).

Eventually, you should get 40/40 from the auto-grader.
